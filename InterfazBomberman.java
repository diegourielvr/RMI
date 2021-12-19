import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Métodos remotos
 */
public interface InterfazBomberman extends Remote {

    /** 
     * Iniciar una partida para N jugadores. 
     * Regresa true si la partida fue creada correctamente de lo contrario regresa false. 
     */
    public boolean nuevaPartida(int N) throws RemoteException;

    /**
     * Registrar nombre como nuevo jugador en una partida.
     * Regresa la posicion de inicio, id del jugador y la información del mundo
     */
    public InterfazInformacion nuevoJugador(String nombre) throws RemoteException;

    /**
     * Preguntar repetidamente si estan los N jugadores.
     * Regresa el numero de jugadores que se encuentran en la partida 
     */
    public int partidaLista() throws RemoteException;

    /**
     * El jugador (cliente) indica que se movio de posicion.
     * Actualiza las coordenadas x, y del jugador indicado por id.
     */
    public void movimiento(int id, int x, int y) throws RemoteException; 

    /**
     * Devuelve al cliente la posicion de los jugadores y de las bombas (Listas)
     */
    public InterfazEstadoPartida obtenerEstado() throws RemoteException;
    
    // public void ponerBomba(int x, int y) throws RemoteException;

    // public void eliminacion(int id) throws RemoteException;

}
