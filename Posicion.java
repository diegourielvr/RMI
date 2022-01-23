public class Posicion {
    private int x;
    private int y;

    Posicion(){
        this.x = 0;
        this.y = 0;
    }

    Posicion (int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() { 
        return this.x; 
    }

    public void setX(int x){ 
        this.x = x; 
    }

    public int getY() { 
        return this.y; 
    }

    public void setY(int y) { 
        this.y = y; 
    }
}
