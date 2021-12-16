package RMI;

import java.io.Serializable;
/**
 * Cuando pasamos un objeto Serializable como parametro de un metodo remoto,
 * java se encarga de convertir este objeto a bytes (serializar el objeto),
 * enviarlo por red y hacerlo llegar al otro lado (el servidor).
 * Alli se encarga de convertir nuevamente esos bytes a un objeto (deserailizar el objeto)
 * Por lo que existiran dos instancias distintas de las clases, una copia de la otra 
 */

/**
 * Interface para el valor de retorno de la funcion nuevoJugador(nombre)
 */

/* Hereda de Serializable */
public interface InterfazInformacionInicial extends Serializable{
    
    public InterfazPosicion getPosicionInicial();

    public int getId();

    public InterfazMapa getInformacionMapa();
}
