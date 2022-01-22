import java.util.ArrayList;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class BombermanServidor implements InterfazBomberman {

    private Mapa mapa;
    private int contIdBombas; // A cada bomba se le asigna un id
    private int totalJugadores; // Total de jugadores en la partida
    private int jugadoresVivos; // Jugadores restantes en la partida
    private boolean partidaActiva; // false: sin partida
    private ArrayList<Jugador> listaJugadores;
    private ArrayList<Bomba> listaBombas;
    private ArrayList<Posicion> posIniciales; // Posicion del mapa en la que aparece cada jugador
    
    BombermanServidor() {
        this.mapa = new Mapa();
        this.totalJugadores = 0;
        this.jugadoresVivos = 0;
        this.partidaActiva = false;
        this.listaJugadores = new ArrayList<Jugador>();
        this.listaBombas = new ArrayList<Bomba>();
        this.posIniciales = new ArrayList<Posicion>();
        initPosIniciales();
        this.contIdBombas = 0;
    }
    
    private void initPosIniciales() {
        this.posIniciales.add(new Posicion (1, 1));
        this.posIniciales.add(new Posicion (1, mapa.getCol() - 2));
        this.posIniciales.add(new Posicion (mapa.getRen() - 2, 1));
        this.posIniciales.add(new Posicion (mapa.getRen() - 2, mapa.getCol() - 2));
    }

    public boolean nuevaPartida(int N){
        System.out.println("[solicitud]: Crear nueva partida.");
        if (!partidaActiva && N > 0) { // Almenos un jugador (prueba)
            partidaActiva = true;
            totalJugadores = N;
            mapa.cargarMapa("map.txt");
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

    public int partidaLista() { 
        return jugadoresVivos;
    }

    public void movimiento(int id , int x, int y) {
        listaJugadores.get(id).setX(x);
        listaJugadores.get(id).setY(y);
        // System.out.println("jugador: " + id + " mov: (" + x + ", " + y + ")");
    }

    public InterfazEstadoPartida obtenerEstado() {
        EstadoPartida nuevoEstado = new EstadoPartida(listaJugadores, listaBombas);
        return nuevoEstado; 
    }

    public void ponerBomba(int idPropietario, int x, int y) {
        int idBomba = contIdBombas;
        contIdBombas += 1;
        listaBombas.add(new Bomba(idBomba, x, y, idPropietario));
    }

    public void quitarBomba(int idBomba){
        for (Bomba bomba : listaBombas) {
            if (bomba.getIdBomba() == idBomba){
                listaBombas.remove(bomba);
                return;
            }
        }
    }

    public void eliminacion(int id){
        System.out.println("[solicitud]: Eliminar jugador " + id);
        for (int i = 0; i < listaJugadores.size(); i++) {
            Jugador jugador = listaJugadores.get(i);
            if (jugador.getId() == id) {
                listaJugadores.remove(i);
                return;
            }
        }
    }

    public void reiniciarPartida() {
        this.partidaActiva = false;
        this.totalJugadores = 0;
        this.jugadoresVivos = 0;
        this.contIdBombas = 0;
        this.listaJugadores.clear();
        this.listaBombas.clear();
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