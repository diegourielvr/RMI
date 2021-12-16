package RMI.bomberman_servidor;

public class Posicion implements InterfazPosicion{
    
    int x;
    int y;

    public Posicion(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
