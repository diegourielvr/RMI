public class Bomba implements InterfazBomba{
    int idBomba;
    int x;
    int y;
    int idPropietario;// El propietario debe eliminar la bomba
    boolean estadoBomba;//False: Sin explotar - True: Explotada

    Bomba() {

    }

    Bomba(int idBomba, int x, int y, int idPropietario) {
        this.idBomba = idBomba;
        this.x = x;
        this.y = y;
        this.idPropietario = idPropietario;// El propietario debe eliminar la bomba
        this.estadoBomba = false;
    }

    public int getIdBomba() {
        return idBomba;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getIdPropietario() {
        return idPropietario;
    }
    public boolean getEstadoBomba(){
        return estadoBomba;
    }
}
