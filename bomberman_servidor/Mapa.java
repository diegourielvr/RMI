package RMI.bomberman_servidor;

public class Mapa implements InterfazMapa{

    private int renglones;
    private int columnas;
    private int cuadricula[][];

    public Mapa(int ren, int col){
        this.renglones = ren;
        this.columnas = col;
        this.cuadricula = new int[ren][col];
    }

    public int getRenglones (){
        return renglones;
    }

    public int getColumnas(){
        return columnas;
    }

    public int[][] getCuadricula(){
        return cuadricula;
    }
}
