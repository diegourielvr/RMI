public class Jugador implements InterfazJugador{
    private int id;
    private int pos_x;
    private int pos_y;
    private boolean estado;
    private String nombre;

    Jugador (){
        this.id = -1;
        this.pos_x = 0;
        this.pos_y = 0;
        this.estado = false;
        this.nombre = "";
    }

    Jugador (int id, int x, int y, boolean estado, String nombre){
        this.id = id;
        this.pos_x = x;
        this.pos_y = y;
        this.estado = estado;
        this.nombre = nombre;
    }

    public int getId() { 
        return this.id; 
    }

    public void setId(int nuevoId) { 
        this.id = nuevoId;
    }

    public int getX() { 
        return this.pos_x; 
    }

    public void setX (int nuevoX) { 
        this.pos_x = nuevoX; 
    }

    public int getY() { 
        return this.pos_y; 
    }

    public void setY (int nuevoY) { 
        this.pos_y = nuevoY; 
    }

    public boolean getEstado() { 
        return this.estado; 
    }
    public void setEstado(boolean nuevoEstado) { 
        this.estado = nuevoEstado; 
    }

    public String getNombre() { 
        return this.nombre; 
    }

    public void setNombre (String nuevoNombre) { 
        this.nombre = nuevoNombre; 
    }
}
