import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Frame;
import java.rmi.RemoteException;
public class MovimientoJugador implements KeyListener {
    int x;
    int y;
    int id;
    int ren;
    int col;
    InterfazBomberman stub;
    MovimientoJugador (int id, int x, int y, int ren, int col, InterfazBomberman stub){
        this.id = id;
        this.x = x;
        this.y = y;
        this.ren = ren;
        this.col = col;
        this.stub = stub;
    }
    public void escucha (){
        Frame f = new Frame();
        f.setVisible(true);
        f.addKeyListener(this);
    }
    
    @Override
    public void keyPressed (KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            if (x != 1)
                x -= 1;
            // System.out.println("UP");
        }
        
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            if (y != col-2)
                y += 1;
            
            // System.out.println("RIGHT");
        }
    
        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            if (x != ren-2)
                x += 1;
            // System.out.println("DOWN");
        }
        
        
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            if (y != 1)
                y -= 1;
            // System.out.println("LEFT");
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
