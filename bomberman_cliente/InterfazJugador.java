import java.io.Serializable;

/* Hereda de Serializable */
public interface InterfazJugador extends Serializable{
    
    public int getId();

    public InterfazPosicion getPosicion();

    public String getNombre();

    public boolean getEstado();

}
