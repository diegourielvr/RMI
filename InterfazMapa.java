package RMI;

import java.io.Serializable;

/* Hereda de Serializable */
public interface InterfazMapa extends Serializable{
    
    public int getAncho();
    
    public int getAlto();
    
    public int[][] getCuadricula();

}
