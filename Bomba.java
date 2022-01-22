public class Bomba implements InterfazBomba{
    private int idBomba;
    private int x;
    private int y;
    private int idPropietario;// El propietario debe eliminar la bomba
    private boolean estadoBomba;//False: Sin explotar - True: Explotada
    private int ticksParaExplotar;
    private int tickActual;
    Bomba() {

    }

    Bomba(int idBomba, int x, int y, int idPropietario) {
        this.idBomba = idBomba;
        this.x = x;
        this.y = y;
        this.idPropietario = idPropietario;// El propietario debe eliminar la bomba
        this.estadoBomba = false;//aun si explotar
        this.ticksParaExplotar = 0;
        this.tickActual = 0;
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

    public int getTicksParaExplotar(){
        return this.ticksParaExplotar;
    }

    public void setTicksParaExplotar(int t){
        this.ticksParaExplotar = t;
    }

    public int getTickActual(){
        return this.tickActual;
    }
    
    public void setTickActual(int t){
        this.tickActual = t;
    }


    public int getIdPropietario() {
        return idPropietario;
    }

    public boolean getEstadoBomba(){
        return estadoBomba;// regresa false si aun no explota la bomba
    }

    public void setEstadoBomba(boolean estado){
        this.estadoBomba = estado;
    }
}
