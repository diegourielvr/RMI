import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;
public class MovimientoJugador implements KeyListener {
    private int x;
    private int y;
    private int id;
    private int ren;
    private int col;
    private int [][] mapa;
    private InterfazBomberman stub;
    private static int PARED = 1;
    private static int BOMBA = 4;
    
    MovimientoJugador (int id, int x, int y, int ren, int col, InterfazBomberman stub){
        this.id = id;//id de jugador
        this.x = x;
        this.y = y;
        this.ren = ren;
        this.col = col;
        this.stub = stub;
        this.mapa =  new int[ren][col];
    }
    public void escucha (){
        Frame f = new Frame();
        f.setVisible(true);
        f.addKeyListener(this);
    }

    // AReferencia a mapa con Objetos
    public void setMapa (int[][] mapa){
        this.mapa = mapa;
    }
    
    public boolean validarRadio(int b_x, int b_y){//recibe la posicion de la bobma
        boolean jugadorMuerto = false;
        // int radio = 3;
        return jugadorMuerto;
    }
    @Override
    public void keyPressed (KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            if (mapa[x-1][y] != PARED && mapa[x-1][y] != BOMBA)
                x -= 1;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            if (mapa[x][y+1] != PARED && mapa[x][y+1] != BOMBA)
                y += 1;
        }
    
        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            if (mapa[x+1][y] != PARED && mapa[x+1][y] != BOMBA)
                x += 1;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            if (mapa[x][y-1] != PARED && mapa[x][y-1] != BOMBA)
                y -= 1;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            try {
                stub.ponerBomba(id, x, y);   
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        }
        
        try {
            stub.movimiento(id, x, y);
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
    }
    @Override
    public void keyReleased (KeyEvent e){}

    @Override
    public void keyTyped(KeyEvent e){}
}
