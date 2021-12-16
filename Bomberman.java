package RMI;
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
public interface Bomberman extends Remote{
    
    /** 
     * Iniciar una partida para N jugadores. 
     * Regresa true si la partida fue creada correctamente de lo contrario regresa false. 
     */
    public boolean nuevaPartida(int N) throws RemoteException;

    /**
     * Registrar nombre como nuevo jugador en una partida.
     * Regresa la posicion de inicio y la información del mundo
     */
    public InterfaceInformacionNuevoJugador nuevoJugador(String nombre) throws RemoteException;
}
