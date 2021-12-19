import java.io.Serializable;
/**
 * Informacion inicial para el jugador
 * Devuelve 
 */
public interface InterfazInformacion extends Serializable {
    /**
     * @return id de jugador
     */
    public int getId();

    /**
     *  @return posicion inicial x 
     */
    public int getPosX();
    
    /**
     *  @return posicion inicial y 
     */
    public int getPosY();
    
    /**
     * @return número total de jugadores
     */
    public int getN();
    
    /**
     * @return número de renglones del mapa
     */
    public int getRen();
    
    /**
     * @return número de columnas del mapa
     */
    public int getCol();

    /**
     *  @return informacion del mapa
     */
    public int[][] getMapa();
}
