//package RMI.bomberman_servidor;

import java.io.Serializable;

/* Hereda de Serializable */
public interface InterfazMapa extends Serializable{
    
    public int getRenglones();
    
    public int getColumnas();
    
    public int[][] getCuadricula();

}
