package RMI;

import java.io.Serializable;

/* Hereda de Serializable */
public interface InterfazEstadoPartida extends Serializable{
    
    /**
     * Devuelve la posicion de los jugadores
     */
    public InterfazJugador[] getListaJugadores();

    /**
     * Devuelve la posicion de las bombas
     */
    // public Posicion[] getListaBombas();
}
