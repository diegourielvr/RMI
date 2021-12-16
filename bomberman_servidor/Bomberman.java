package RMI.bomberman_servidor;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Un objeto remoto es una instancia de una clase 
 * que implementa una interfaz remota.
 * 
 * En nuestro caso, definimos la interfaz remota bomberman,
 * que debe extender de java.rmi.Remote
 * y declaramos un conjunto de metodos remotos.
 * 
 * Cada método remoto debe declerar 
 * java.rmi.RemoteException en su cláusula throw.
 */

/* Hereda de Remote */
public interface Bomberman extends Remote{
    
    /** 
     * Iniciar una partida para N jugadores. 
     * Regresa true si la partida fue creada correctamente de lo contrario regresa false. 
     */
    public boolean nuevaPartida(int N) throws RemoteException;

    /**
     * Registrar nombre como nuevo jugador en una partida.
     * Regresa la posicion de inicio, id del jugador y la información del mundo
     */
    public InterfazInformacionInicial nuevoJugador(String nombre) throws RemoteException;

    /**
     * Preguntar repetidamente si estan los N jugadores.
     * Regresa verdadero o falso dependiendo si se cumplen los N jugadores.
     * (opcion permitida) Regresa el numero de jugadores que se encuentran en la partida 
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
