import java.util.ArrayList;

public class EstadoPartida implements InterfazEstadoPartida{
    private ArrayList<Jugador> listaJugadores;
    //private ArrayList<Bomba> listaBombas;

    EstadoPartida () {
        listaJugadores = new ArrayList<Jugador>();
        // listaJugadores = new ArrayList<Jugador>();
    }

    EstadoPartida (ArrayList<Jugador> lista) {
        this.listaJugadores = lista;
        //listaJugadores.clear();;
        //for (Jugador jugador : lista) {
        //    listaJugadores.add(jugador);
        //}   
    }

    public ArrayList<Jugador> getListaJugadores() {
        return listaJugadores;
    }
    public void setListaJugadores(ArrayList<Jugador> lista){
        this.listaJugadores = lista;
        //listaJugadores.clear();
        //for (Jugador jugador: lista) {
        //    listaJugadores.add(jugador);
        //}
    } 
    
}
