//package RMI.bomberman_servidor;

public class EstadoPartida implements InterfazEstadoPartida{
    
    private boolean estadoP = false;  // false: no hay partida - true: partida en curso
    private int numeroJugadores;
    private int jugadoresVivos;
    private Jugador listaJugadores[];
    
    public EstadoPartida(int N) {
        estadoP = false;
        numeroJugadores = N;
        jugadoresVivos = 0;
        listaJugadores = new Jugador[N];
    }

    public boolean getEstado(){
        return estadoP;
    }

    public void partidaEnCurso(){
        estadoP = true;
    }

    public int getNumJugadores(){
        return numeroJugadores;
    }

    public int getJugadoresVivos(){
        return jugadoresVivos;
    }

    public int generaId(){
        return jugadoresVivos - 1;
    }
    public void incrementaJugadoresVivos(){
        jugadoresVivos += 1;
    }

    public InterfazJugador[] getListaJugadores(){
        return listaJugadores;
    }

    public void agregaJugador(int id, String nombre, Posicion pos, int estado){
        listaJugadores[id] = new Jugador(id, nombre, pos, estado);
    }

    public void actualizarPosicion(int id, int x, int y){
        listaJugadores[id].posicion.x = x;
        listaJugadores[id].posicion.y = y;
    }
}
