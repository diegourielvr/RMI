package RMI.bomberman_cliente;

import java.io.Serializable;

/* Hereda de Serializable */
public interface InterfazPosicion extends Serializable{
    
    public int getX();

    public int getY();
    
}
