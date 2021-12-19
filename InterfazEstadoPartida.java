import java.io.Serializable;
import java.util.ArrayList;

public interface InterfazEstadoPartida extends Serializable{
    
    public ArrayList<Jugador> getListaJugadores();
    
    // public ArrayList<Bomba> getListaBombas();
}
