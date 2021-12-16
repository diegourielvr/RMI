package RMI.bomberman_servidor;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Implementar la interfaz remota Bomberan
 */
public class BombermanServidor implements Bomberman {

    /* Atributos */
    private boolean estadoPartida;
    private int numeroJugadores;
    private int jugadoresVivos;
    private InterfazJugador listaJugadores[];
    // lista de bombas

    /* Métodos */
    public BombermanServidor(){}
    
    public boolean nuevaPartida(int N){
        
        return true;
    }

    public InterfazInformacionInicial nuevoJugador(String nombre){
        
        return null;
    }

    public int partidaLista(){

        return 0;
    }

    public void movimiento(int id, int x, int y){

    }

    public InterfazEstadoPartida obtenerEstado(){

        return null;
    }

    /**
     * Crear instancia de la implementacion del objeto remoto (Bomberman Servidor),
     * exportar el objeto remoto y vincular instancia a un nombre 
     * en un registro RMI de java
     */
    public static void main(String[] args) {
        try {
            BombermanServidor obj = new BombermanServidor();
            Bomberman stub =  (Bomberman) UnicastRemoteObject.exportObject(obj, 0);

            /* Vincular el stub del objeto remoto a un nombre en un registro RMI */
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Bomberman", stub);
            System.err.println("BombermanServidor listo!");
        } catch (Exception e) {
            System.err.println("BombermanServidor Eexception: " + e.toString());
            e.printStackTrace();
        }

    }
}