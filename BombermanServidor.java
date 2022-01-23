import java.util.ArrayList;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class BombermanServidor implements InterfazBomberman {
    private Mapa mapa;
    private int idBomba; // A cada bomba se le asigna un id
    private int totalJugadores; // Total de jugadores en la partida
    private int jugadoresVivos; // Jugadores restantes en la partida
    private boolean partidaActiva; // false: sin partida
    private ArrayList<Jugador> listaJugadores;
    private ArrayList<Bomba> listaBombas;
    private ArrayList<Posicion> posIniciales; // Posicion del mapa en la que aparece cada jugador
    
    BombermanServidor() {
        this.mapa = new Mapa();
        this.idBomba = 0;
        this.totalJugadores = 0;
        this.jugadoresVivos = 0;
        this.partidaActiva = false;
        this.listaJugadores = new ArrayList<Jugador>();
        this.listaBombas = new ArrayList<Bomba>();
        this.posIniciales = new ArrayList<Posicion>();
    }
    
    public boolean nuevaPartida(int N){ // Método remoto
        System.out.println("[solicitud]: Crear nueva partida.");
        boolean partidaCreada = false;
        if (!this.partidaActiva && N > 0) {
            this.partidaActiva = true;
            this.totalJugadores = N;
            this.mapa.cargarMapa("map.txt");
            initPosIniciales();
            partidaCreada = true;
        }  
        return partidaCreada;
    }
    
    private void initPosIniciales() {
        this.posIniciales.add(new Posicion (1, 1)); // Jugador 1
        this.posIniciales.add(new Posicion (mapa.getAncho()-2, 1)); // Jugador 2
        this.posIniciales.add(new Posicion (1, mapa.getAlto() - 2)); // Jugador 3
        this.posIniciales.add(new Posicion (mapa.getAncho() - 2, mapa.getAlto() - 2)); // Jugador 4
    }
    
    public InterfazInformacion nuevoJugador(String nombre) {
        System.out.println("[solicitud]: Nuevo jugador (" + nombre + ")");
        if (jugadoresVivos < totalJugadores) {
            int id = jugadoresVivos; 
            jugadoresVivos += 1;

            // Agregar nuevo jugador a la lista
            Jugador nuevoJugador = new Jugador(id, posIniciales.get(id).getX(),
                            posIniciales.get(id).getY(), true, nombre);
                            
            // Devolver informacion del jugador creado y mapa
            Informacion info = new Informacion(id, nuevoJugador.getX(), 
                                    nuevoJugador.getY(), mapa.getAlto(),
                                    mapa.getAncho(), totalJugadores, mapa.getMapa());
            listaJugadores.add(nuevoJugador);
            return info;
        } else 
            return null; // partida llena
    }

    public int partidaLista() { 
        return this.jugadoresVivos;
    }

    public void movimiento(int id, int x, int y) {
        for (Jugador j : listaJugadores) {
            if (j.getId() == id){
                j.setX(x);
                j.setY(y);
                break;
            }
        }
    }

    public InterfazEstadoPartida obtenerEstado() {
        EstadoPartida nuevoEstado = new EstadoPartida(listaJugadores, listaBombas);
        return nuevoEstado; 
    }

    public void ponerBomba(int idPropietario, int x, int y) {
        this.listaBombas.add(new Bomba(this.idBomba, x, y, idPropietario));
        idBomba += 1;
    }

    public void quitarBomba(int idBomba){
        for (Bomba bomba : listaBombas) {
            if (bomba.getIdBomba() == idBomba){
                this.listaBombas.remove(bomba);
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