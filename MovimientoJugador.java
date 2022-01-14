import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
public class MovimientoJugador implements KeyListener {
    private int x;
    private int y;
    private int id;
    private int ren;
    private int col;
    private int [][] mapa;
    private InterfazBomberman stub;
    private static int PARED = -1;
    
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

    public void setMapa (int[][] mapa){
        this.mapa = mapa;
    }
    
    @Override
    public void keyPressed (KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            if (x != 1 && mapa[x-1][y] != PARED)
                x -= 1;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            if (y != col-2 && mapa[x][y+1] != PARED)
                y += 1;
        }
    
        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            if (x != ren-2 && mapa[x+1][y] != PARED)
                x += 1;
        }
        
        
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            if (y != 1 && mapa[x][y-1] != PARED)
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
