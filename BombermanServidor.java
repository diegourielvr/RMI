import java.util.ArrayList;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class BombermanServidor implements InterfazBomberman {
    private Mapa mapa;
    private int totalJugadores;
    private int jugadoresVivos;
    private boolean estadoPartida;
    private ArrayList<Jugador> listaJugadores;
    private ArrayList<Posicion> posIniciales;
    
    BombermanServidor() {
        mapa = new Mapa();
        totalJugadores = 0;
        jugadoresVivos = 0;
        estadoPartida = false;
        listaJugadores = new ArrayList<Jugador>();
        posIniciales = new ArrayList<Posicion>();
        posIniciales.add(new Posicion (1, 1));
        posIniciales.add(new Posicion (1, mapa.getCol() - 2));
        posIniciales.add(new Posicion (mapa.getRen() - 2, 1));
        posIniciales.add(new Posicion (mapa.getRen() - 2, mapa.getCol() - 2));
    }

    public boolean nuevaPartida(int N){
        System.out.println("[solicitud]: Crear nueva partida.");
        if (!estadoPartida && N > 0) { // Almenos un jugador (prueba)
            estadoPartida = true;
            totalJugadores = N;
            return true;
        } 
        else return false; 
    }

    public InterfazInformacion nuevoJugador(String nombre) {
        System.out.println("[solicitud]: Nuevo jugador (" + nombre + ")");
        if (jugadoresVivos < totalJugadores) {
            int id = jugadoresVivos; 
            Jugador nuevoJugador = new Jugador(id, posIniciales.get(id).getX(),
                            posIniciales.get(id).getY(), true, nombre);
            Informacion info = new Informacion(id, nuevoJugador.getX(), 
                            nuevoJugador.getY(), mapa.getRen(),
                            mapa.getCol(), totalJugadores, mapa.getMapa());
            listaJugadores.add(nuevoJugador);
            jugadoresVivos += 1;
            return info;
        }
        else return null; // partida llena
    }

    public int partidaLista() { return jugadoresVivos; }

    public void movimiento(int id , int x, int y) {
        listaJugadores.get(id).setX(x);
        listaJugadores.get(id).setY(y);
        System.out.println("mov: " + id + " (" + x + ", " + y + ")");
    }

    public InterfazEstadoPartida obtenerEstado() {
        EstadoPartida nuevoEstado = new EstadoPartida(listaJugadores);
        return nuevoEstado; 
    }

    public void reiniciarPartida() {
        this.estadoPartida = false;
        this.totalJugadores = 0;
        this.jugadoresVivos = 0;
        this.listaJugadores.clear();
    }

    public static void main(String[] args) {
        try {
            BombermanServidor obj = new BombermanServidor();
            InterfazBomberman stub = (InterfazBomberman) UnicastRemoteObject.exportObject(obj, 0);

            /* Vincular el stub del objeto remoto a un nombre en un registro RMI */
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Bomberman", stub);
            System.err.println("BombermanServidor listo!");
        } catch (Exception e) {
            System.err.println("BombermanServidor exception: " + e.toString());
            e.printStackTrace();
        }
    }

}