import java.io.Serializable;
/**
 * Informacion inicial para el jugador
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
    public int getAlto();
    
    /**
     * @return número de columnas del mapa
     */
    public int getAncho();

    /**
     *  @return informacion del mapa
     */
    public int[][] getMapa();
}
