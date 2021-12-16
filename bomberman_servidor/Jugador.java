public class Jugador implements InterfazJugador{
    int id;
    String nombre;
    Posicion posicion;
    int estado; // 0: Muerto - 1: Vivo

    public Jugador(int id, String nombre, Posicion pos, int estado){
        this.id = id;
        this.nombre = nombre;
        this.posicion = pos;
        this.estado = estado;
    }

    public int getId(){
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    public InterfazPosicion getPosicion(){
        return posicion;
    } 

    public int getEstado(){
        return estado;
    }
}
