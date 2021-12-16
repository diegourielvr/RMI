//package RMI.bomberman_servidor;

import java.io.Serializable;

/* Hereda de Serializable */
public interface InterfazEstadoPartida extends Serializable{
    
    public boolean getEstado();

    public void partidaEnCurso();

    public int getNumJugadores();

    public int getJugadoresVivos();

    public int generaId();

    public void incrementaJugadoresVivos();
  
    public InterfazJugador[] getListaJugadores();

    public void agregaJugador(int id, String nombre, Posicion pos, int estado);
    
    public void actualizarPosicion(int id, int x, int y);
}
