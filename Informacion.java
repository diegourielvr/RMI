public class Informacion implements InterfazInformacion{
    private int id;
    private int x;
    private int y;
    private int N;
    private int alto;
    private int ancho;
    private int[][] mapa;

    Informacion() {
        this.id = 0;
        this.x = 0;
        this.y = 0;
        this.N = 0;
        this.alto = 0;
        this.ancho = 0;
    }

    Informacion(int id, int x, int y, int alto, int ancho, int N, int[][] mapa) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.N = N;
        this.alto = alto;
        this.ancho = ancho;
        this.mapa = mapa;
    }

    public int getId() { 
        return this.id; 
    }

    public int getPosX() { 
        return this.x; 
    }
    
    public int getPosY() { 
        return this.y; 
    }
    
    public int getN() { 
        return this.N; 
    }
    
    public int getAlto() { 
        return this.alto; 
    }
    
    public int getAncho() { 
        return this.ancho; 
    }

    public int[][] getMapa() { 
        return this.mapa; 
    }
}
