import java.io.Serializable;

public interface InterfazJugador extends Serializable{
    
    public int getId();
    public void setId(int nuevoId);

    public int getX();
    public void setX (int nuevoX);

    public int getY();
    public void setY (int nuevoY);

    public boolean getEstado();
    public void setEstado(boolean nuevoEstado);

    public String getNombre();
    public void setNombre (String nuevoNombre);

}
