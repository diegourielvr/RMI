import java.io.Serializable;

public interface InterfazBomba extends Serializable{
    
    public int getIdBomba();

    public int getX();

    public int getY();

    public int getIdPropietario();
    
    public boolean getEstadoBomba();
}