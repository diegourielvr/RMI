import java.util.ArrayList;

public class EstadoPartida implements InterfazEstadoPartida {
    private ArrayList<Jugador> listaJugadores;
    private ArrayList<Bomba> listaBombas;

    EstadoPartida () {
        listaJugadores = new ArrayList<Jugador>();
        listaBombas = new ArrayList<Bomba>();
    }

    EstadoPartida (ArrayList<Jugador> listaJ, ArrayList<Bomba>listaB ) {
        this.listaJugadores = listaJ;
        this.listaBombas = listaB; 
    }

    public ArrayList<Jugador> getListaJugadores() {
        return this.listaJugadores;
    }

    public ArrayList<Bomba> getListaBombas() {
        return this.listaBombas;
    }
}
