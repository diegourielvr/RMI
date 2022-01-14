public class Mapa {
    private int ren;
    private int col;
    private int[][] mapa;
    public int VACIO = 0;
    public int PARED = -1;
    public int JUGADOR = 1;
    public int JUGADOR_EXTERNO = 2;
    public int BOMBA = 3;


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
                if (i % 2 == 0 && j % 2 == 0){
                    this.mapa[i][j] = PARED;
                }
            }
        }
    }

    public void cargarMapa(String archivo){

    }

    public void asignaObjeto(int ren ,int col, int obj){
        this.mapa[ren][col] = obj;
    }

    public int getRen() { 
        return this.ren; 
    }

    public void setRen(int ren){
        this.ren = ren;
    } 

    public int getCol() { 
        return this.col; 
    }

    public void setCol(int col){
        this.col = col;
    }

    public int[][] getMapa() { 
        return this.mapa; 
    }

    public void setMapa (int[][] nuevoMapa){
        for (int i = 0; i < ren; i++) {
            for (int j = 0; j < col; j++) {
                this.mapa[i][j] = nuevoMapa[i][j];
            }
        }
    }

    public void mostrarMapa(){
        for (int i = 0; i < ren; i++) {
            for (int j = 0; j < col; j++) {
                if (mapa[i][j] == PARED)
                    System.out.print("🌀");
                if (mapa[i][j] == VACIO)
                    System.out.print("  ");//🔲
                if (mapa[i][j] == JUGADOR)
                    System.out.print("🥶");
                if (mapa[i][j] == JUGADOR_EXTERNO)
                    System.out.print("🥵");
                if (mapa[i][j] == BOMBA)
                    System.out.print("💣");
            }
            System.out.println("");
        }     
    }
}
