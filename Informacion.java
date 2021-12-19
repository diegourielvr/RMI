public class Informacion implements InterfazInformacion{
    private int id;
    private int x;
    private int y;
    private int N;
    private int ren;
    private int col;
    private int[][] mapa;

    Informacion() {
        this.id = 0;
        this.x = 0;
        this.y = 0;
        this.N = 0;
        this.ren = 0;
        this.col = 0;
    }

    Informacion(int id, int x, int y, int ren, int col, int N, int[][] mapa) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.N = N;
        this.ren = ren;
        this.col = col;
        this.mapa = mapa;
    }

    public int getId() { return this.id; }

    public int getPosX() { return this.x; }
    
    public int getPosY() { return this.y; }
    
    public int getN() { return this.N; }
    
    public int getRen() { return this.ren; }
    
    public int getCol() { return this.col; }
    
    public int[][] getMapa() { return this.mapa; }
}
