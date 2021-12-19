public class Mapa {
    private int ren;
    private int col;
    private int[][] mapa;
    private final int VACIO = 0;
    private final int PARED = -1;


    Mapa() {
        this.ren = 17;
        this.col = 17;
        mapa = new int[ren][col];
        for (int i = 0; i < ren; i++) {
            for (int j = 0; j < col; j++) {
                if (i == 0 || i == ren-1 || j == 0 || j == col-1){ // Bordes
                    this.mapa[i][j] = PARED;
                }
                else {
                    this.mapa[i][j] = VACIO;
                }
                // if (i % 2 == 0 && j % 2 == 0){
                //     this.mapa[i][j] = PARED;
                // }
            }
        }
    }
    
    Mapa(int ren, int col) {
        this.ren = ren;
        this.col = col;
        
        for (int i = 0; i < ren; i++) {
            for (int j = 0; j < col; j++) {
                if (i == 0 || i == ren-1 || j == 0 || j == col-1) // Bordes
                    this.mapa[i][j] = PARED;
                else 
                    this.mapa[i][j] = VACIO;
            }
        }
    }

    public int getRen() { return this.ren; }
    
    public int getCol() { return this.col; }
    
    public int[][] getMapa() { return this.mapa; }
}
