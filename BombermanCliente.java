import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Frame;

public class BombermanCliente implements KeyListener{
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
    
    private static int VACIO = 0;
    private static int PARED = -1;
    private static int JUGADOR = 1;
    public BombermanCliente() {
        id = 0;
        nombre = "";
        x = 0;
        y = 0;
        N = 0;
        ren = 0;
        col = 0;
        jugadoresVivos = 0;
        stub = null;
        listaJugadores = new ArrayList<Jugador>();;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            if (y != 0)
                y -= 1;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            if (x != ren-1)
                x += 1;
        }
    
        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            if (y != col-1)
                y += 1;
        }
        
        
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            if (x != 0)
                x -= 1;
        }

        /* Notificar movimiento */
        try {
            stub.movimiento(id, x, y);
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void keyReleased(KeyEvent e){}

    @Override
    public void keyTyped(KeyEvent e){}

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String host = (args.length < 1) ? null : args[0];
        System.out.println("\t\t- BOMBERMAN GAME -");

        try {
            Registry registry = LocateRegistry.getRegistry(host);// null indica por defecto 'localhost'
            stub = (InterfazBomberman) registry.lookup("Bomberman");// Localizar servicio
            
            if (!stub.nuevaPartida(0)) { // No hay partida activa
                System.out.println("Ingresa el número de jugadores (Entre 2 y 4)");
                System.out.print("\t)> ");
                N = sc.nextInt();
                stub.nuevaPartida(N);
            }
            
            System.out.println("Ingresa la frecuencia de actualizacion (máximo: 90)");
            System.out.println("\t)> ");
            int frecuencia = sc.nextInt();

            System.out.println("Ingresa tu apodo");
            System.out.print("\t)> ");
            nombre = sc.nextLine();

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
                }
            }

            boolean finPartida = false;
            int fps = 1000 / frecuencia;

            while (!finPartida) {
                // Obtener estado cada tiempo
                try {
                    InterfazEstadoPartida nuevoEstado = stub.obtenerEstado();
                    listaJugadores = nuevoEstado.getListaJugadores();
                    int[][] nuevoMapa = mapa.clone();
                    for (Jugador jugador : listaJugadores) {
                        nuevoMapa[jugador.getX()][jugador.getY()] = JUGADOR;
                    }
                    for (int i = 0; i < ren; i++) {
                        for (int j = 0; j < col; j++) {
                            if (nuevoMapa[i][j] == PARED)
                                System.out.print("#");
                            if (nuevoMapa[i][j] == VACIO)
                                System.out.print(" ");
                            if (nuevoMapa[i][j] == JUGADOR)
                                System.out.print("&");
                        }
                        System.out.println("");
                    }
                    Thread.sleep(fps);

                } catch (Exception e) {
                    System.err.println("Exception del cliente: " + e.toString());
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            System.err.println("Excepcion del Cliente: " + e.toString());
            e.printStackTrace();
        }
        sc.close();
        BombermanCliente b = new BombermanCliente(); 
        Frame f = new Frame("Bomberman");
        f.addKeyListener(b);
    }

}