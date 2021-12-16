public class InformacionInicial implements InterfazInformacionInicial{
    
    private Posicion posicion;
    private int id;
    private Mapa mapa;

    public InformacionInicial(Posicion pos, int id, Mapa mapa){
        this.posicion = pos;
        this.id = id;
        this.mapa = mapa;
    }

    public Posicion getPosicionInicial(){
        return posicion;
    }

    public int getId (){
        return id;
    }

    public Mapa getInformacionMapa(){
        return mapa;
    }
}
