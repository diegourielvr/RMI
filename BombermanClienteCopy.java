import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

public class BombermanClienteCopy {
    private static int id;
    private static int x;
    private static int y;
    private static int N;
    private static int ren;
    private static int col;
    private static String nombre;
    private static int [][] mapa;
    private static InterfazBomberman stub;
    private static int jugadoresVivos;
    private static ArrayList<Jugador> listaJugadores;
    private static ArrayList<Bomba> listaBombas;

    private static int VACIO = 0;
    private static int PARED = -1;
    private static int JUGADOR = 1;
    private static int JUGADOR_EXTERNO = 2;
    private static int BOMBA = 3;

    public BombermanClienteCopy() {
    }

    private static void actualizarMapa(int nuevoMapa[][]){
        for (int i = 0; i < ren; i++){
            for (int j = 0; j < col; j++) {
                nuevoMapa[i][j] = mapa[i][j];
            }
        }
        
        for (Jugador jugador : listaJugadores) {
            if  (jugador.getId() == id){
                nuevoMapa[jugador.getX()][jugador.getY()] = JUGADOR;
            } else {
                nuevoMapa[jugador.getX()][jugador.getY()] = JUGADOR_EXTERNO;
            }
        }

        for (Bomba bomba : listaBombas) {
            // La bomba aun no explota
            if (bomba.getEstadoBomba() == false) {
                mapa[bomba.getX()][bomba.getY()] = BOMBA;
            }
        }       
    }
    private static void dibujarMapa(int nuevoMapa[][]){
        for (int i = 0; i < ren; i++) {
            for (int j = 0; j < col; j++) {
                if (nuevoMapa[i][j] == PARED)
                    System.out.print("🌀");
                if (nuevoMapa[i][j] == VACIO)
                    System.out.print("  ");//🔲
                if (nuevoMapa[i][j] == JUGADOR)
                    System.out.print("🥶");
                if (nuevoMapa[i][j] == JUGADOR_EXTERNO)
                    System.out.print("🥵");
                if (nuevoMapa[i][j] == BOMBA)
                    System.out.print("💣");
                    // System.out.flush();
            }
            System.out.println("");
        }               
    }

    private static void limpiarPantalla(){
        System.out.print("\033[H\033[2J");
        // System.out.flush();
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String host = (args.length < 1) ? null : args[0];
        System.out.println("\t\t- BOMBERMAN GAME -");

        try {
            Registry registry = LocateRegistry.getRegistry(host);// null indica por defecto 'localhost'
            stub = (InterfazBomberman) registry.lookup("Bomberman");// Localizar servicio
            
            if (!stub.nuevaPartida(0)) { // No hay partida activa
                System.out.println("Ingresa el número de jugadores (Entre 2 y 4):");
                N = sc.nextInt();
                stub.nuevaPartida(N);
            }
            
            System.out.println("Ingresa la frecuencia de actualizacion (Recomendado: >40):");
            // System.out.flush();
            int frecuencia = sc.nextInt();

            System.out.print("Ingresa tu apodo:");
            // System.out.flush();
            nombre = sc.next();

            InterfazInformacion info = stub.nuevoJugador(nombre);
            if (info == null ){
                System.out.println("La partida se encuentra llena");
                System.out.println("Intenta más tarde!");
                System.exit(0);
            }
            System.out.println("Uniendose a una partida...");
            
            id = info.getId();
            N = info.getN();
            x = info.getPosX();
            y = info.getPosY();
            ren = info.getRen();
            col = info.getCol();
            mapa = info.getMapa();

            int auxJugadoresVivos = 0;
            
            while (jugadoresVivos < N) {
                jugadoresVivos = stub.partidaLista();
                if (auxJugadoresVivos != jugadoresVivos){
                    System.out.println("\t" + jugadoresVivos + "/" + N + "  jugadores");
                    auxJugadoresVivos = jugadoresVivos;
                }
            }

            MovimientoJugador mJ = new MovimientoJugador(id, x, y, ren, col, stub);
            mJ.escucha();

            boolean finPartida = false;
            int fps = 1000 / frecuencia;
            
            int[][] nuevoMapa = new int[ren][col];
            
            while (!finPartida) {
                // Obtener estado cada tiempo
                try {
                    InterfazEstadoPartida nuevoEstado = stub.obtenerEstado();
                    listaJugadores = nuevoEstado.getListaJugadores();

                    /**
                     * Si hay nuevas bombas entonces creamos un hilo para esa bomba
                     */
                    listaBombas = nuevoEstado.getListaBombas();

                    actualizarMapa(nuevoMapa);
                    
                    mJ.setMapa(nuevoMapa);// Notificar de las nuevas entidades

                    dibujarMapa(nuevoMapa);

                    Thread.sleep(fps);

                    limpiarPantalla();

                } catch (Exception e) {
                    System.err.println("[Exception del cliente]: " + e.toString());
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            System.err.println("Excepcion del Cliente: " + e.toString());
            e.printStackTrace();
        }
        sc.close();
    }

}