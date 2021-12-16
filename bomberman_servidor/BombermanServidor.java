package RMI.bomberman_servidor;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Implementar la interfaz remota Bomberan
 */
public class BombermanServidor implements Bomberman {
    
    /* Atributos */
    private final int RENGLONES = 20;
    private final int COLUMNAS = 20;
    private final int MUERTO = 0;
    private final int VIVO = 1;
    private EstadoPartida partida;
    private Mapa mapa;
    private Posicion[] posIniciales;

    /* Métodos */
    public BombermanServidor(){
        partida = new EstadoPartida(0);
        Posicion pos1 = new Posicion(0, 0);
        Posicion pos2 = new Posicion(RENGLONES - 1, 0);
        Posicion pos3 = new Posicion(0, COLUMNAS - 1);
        Posicion pos4 = new Posicion(RENGLONES - 1, COLUMNAS - 1);
        posIniciales = new Posicion[]{pos1, pos2, pos3, pos4};
    }
    
    public boolean nuevaPartida(int N){
        if (partida.getEstado() == false){ // Crear una nueva partida
            partida = new EstadoPartida(N);
            partida.partidaEnCurso(); // Establecer partiad en curso
            mapa = new Mapa(RENGLONES, COLUMNAS);
            return true;
        } else {
            return false;
        }
    }

    public InterfazInformacionInicial nuevoJugador(String nombre){
        if (partida.getJugadoresVivos() < partida.getNumJugadores()) {
            partida.incrementaJugadoresVivos(); // Incrementa en 1 el numerod e jugadores
            int id = partida.generaId();
            partida.agregaJugador(id, nombre, posIniciales[id], VIVO);
            InformacionInicial infoInicial = new InformacionInicial(posIniciales[id], id, mapa);
            return infoInicial;
        } else {
            return null;
        }
    }

    public int partidaLista(){
        return partida.getJugadoresVivos();
    }

    public void movimiento(int id, int x, int y){
        partida.actualizarPosicion(id, x, y);
    }

    public InterfazEstadoPartida obtenerEstado(){
        return partida;
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