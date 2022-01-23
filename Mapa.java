import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Mapa implements InterfazMapa {
    private int alto; // ren
    private int ancho; // col
    private int[][] mapa;
    
    Mapa() {
        this.alto = 17;
        this.ancho = 17;
    }

    public void initMapa() {
        this.mapa = new int[this.alto][this.ancho];
    }
    public void cargarMapa(String archivo){
        File f = new File (archivo);
        Scanner sc = null;
        try {   
            sc =  new Scanner(f);
        } catch (FileNotFoundException e) {
            System.out.println("Error al abrir el archivo");
        }

        // Obtener ancho y alto del mapa
        if (sc.hasNext())
            this.alto = Integer.parseInt(sc.next());
        if (sc.hasNext())
            this.ancho = Integer.parseInt(sc.next());

        this.mapa = new int[this.alto][this.ancho];
        // Obtener objetos del mapa
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                if (sc.hasNext()) {
                    this.mapa[i][j] = Integer.parseInt(sc.next());
                }
            }
        }
        sc.close();       
    }

    public void agregarEntidad(int x, int y, int ent) {
        this.mapa[y][x] = ent;
    }

    public int getAlto() { 
        return this.alto; 
    }

    public void setAlto(int alto) {
        this.alto = alto;
    } 

    public int getAncho() { 
        return this.ancho; 
    }

    public void setAncho(int ancho){
        this.ancho = ancho;
    }

    public int[][] getMapa() { 
        return this.mapa; 
    }

    public void setMapa (int[][] nuevoMapa){
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                mapa[i][j] = nuevoMapa[i][j];
            }
        }
    }

    public void mostrarMapa(){
        for (int i = 0; i < this.alto; i++) {
            for (int j = 0; j < this.ancho; j++) {
                if (mapa[i][j] == PARED)
                    System.out.print("💠");//🌀
                if (mapa[i][j] == VACIO)
                    System.out.print("  ");//🔲
                if (mapa[i][j] == JUGADOR)
                    System.out.print("🐼");
                if (mapa[i][j] == ENEMIGO)
                    System.out.print("🐻");
                if (mapa[i][j] == BOMBA)
                    System.out.print("🔥");//💣💥
            }
            System.out.println();
        }     
    }

    public boolean jugadorEnRadio(int x, int y, int radio){
        boolean jugadorEliminado = false;
        int i = 0;
        while (i <= radio && mapa[y][x - i] != PARED && jugadorEliminado == false){//Izq
            if (mapa[y][x - i] == JUGADOR)
                jugadorEliminado = true;
            i++;
        }
        i = 0;
        while (i <= radio && mapa[y - i][x] != PARED && jugadorEliminado == false){//arriba
            if (mapa[y - i][x] == JUGADOR)
                jugadorEliminado = true;
            i++;
        }
        i = 0;
        while (i <= radio && mapa[y][x + i] != PARED && jugadorEliminado == false){//der
            if (mapa[y][x + i] == JUGADOR)
                jugadorEliminado = true;
            i++;
        }
        i = 0;
        while (i <= radio && mapa[y + i][x] != PARED && jugadorEliminado == false){//abajo
            if (mapa[y + i][x] == JUGADOR)
                jugadorEliminado = true;
            i++;
        }
        return jugadorEliminado;
    }
}
