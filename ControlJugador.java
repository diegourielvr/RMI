import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;

public class ControlJugador implements KeyListener, InterfazMapa {
    private int x; // pos x del jugador
    private int y; // pos y del jugador
    private int id; // id del jugador
    private int [][] mapa;
    private InterfazBomberman stub;
    
    ControlJugador (int id, int x, int y, InterfazBomberman stub){
        this.id = id;
        this.x = x;
        this.y = y;
        this.stub = stub;
    }

    public void escucha (String nombre){
        Frame f = new Frame(nombre);
        f.setVisible(true);
        f.addKeyListener(this);
    }

    // Referencia a mapa con entidades
    public void sincronizarMapa (int[][] mapa){
        this.mapa = mapa;
    }

    @Override
    public void keyPressed (KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            if (mapa[y-1][x] != PARED && mapa[y-1][x] != BOMBA)
                y -= 1;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            if (mapa[y][x+1] != PARED && mapa[y][x+1] != BOMBA)
                x += 1;
        }
    
        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            if (mapa[y+1][x] != PARED && mapa[y+1][x] != BOMBA)
                y += 1;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            if (mapa[y][x-1] != PARED && mapa[y][x-1] != BOMBA)
                x -= 1;
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
