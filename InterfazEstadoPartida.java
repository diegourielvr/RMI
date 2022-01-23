import java.io.Serializable;
import java.util.ArrayList;

/**
 * Permite obtener la lista de jugadores y bombas
 */
public interface InterfazEstadoPartida extends Serializable{
    
    public ArrayList<Jugador> getListaJugadores();
    
    public ArrayList<Bomba> getListaBombas();
}
