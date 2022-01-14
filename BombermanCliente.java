import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class BombermanCliente {
    private int N; 
    private int fps;
    private Mapa mapa;
    private Mapa mapaObjetos; // Mapa con objetos
    private Jugador jugador;
    private int jugadoresVivos;
    private MovimientoJugador m;
    private InterfazBomberman stub;
    private ArrayList<Bomba> listaBombas;
    private ArrayList<Jugador> listaJugadores;

    public BombermanCliente() {
        this.N = 0;
        this.m = null;
        this.mapa = new Mapa();
        this.mapaObjetos = new Mapa();
        this.jugador = new Jugador();
        this.jugadoresVivos = 0;
        this.stub = null;
        this.listaJugadores = new ArrayList<Jugador>();;
        this.listaBombas = new ArrayList<Bomba>();;
    }

    public void asociarStub(InterfazBomberman Stub){
        this.stub = Stub;
    }

    public boolean buscarPartida(int N){
        boolean estadoPartida = false;
        try {
            estadoPartida = stub.nuevaPartida(N);
        } catch (Exception e) {
            System.err.println("Error del cliente");
            e.printStackTrace();
        }
        return estadoPartida;
    }

    public void setFrecuencia(int frecuencia){
        this.fps = (int) (1000 / frecuencia);
        System.out.println("fps: "+ fps);
    }

    public boolean unirsePartida(String nombre){
        InterfazInformacion info = null;
        try {
            info = stub.nuevoJugador(nombre);
        } catch (Exception e) {
            System.err.println("Error del cliente: nuevoJugador");
            e.printStackTrace();
        }
        if (info != null){
            this.jugador.setNombre(nombre);
            this.jugador.setId(info.getId());
            this.jugador.setX(info.getPosX());//pos inicial x
            this.jugador.setY(info.getPosY());//pos inicial y
            this.jugador.setEstado(true);// Jugador vivo
            this.mapa.setRen(info.getRen());
            this.mapa.setCol(info.getCol());
            this.mapa.setMapa(info.getMapa());
            this.N = info.getN();// Numero de jugadores
            this.mapaObjetos.setRen(info.getRen());
            this.mapaObjetos.setCol(info.getCol());
            return true;
        } 
        else return false;
    }

    public void esperarJugadores(){
        int auxJugadoresVivos = 0;
        while (jugadoresVivos < N){
            //posible error: agregar try-catch
            try {
                jugadoresVivos = stub.partidaLista();    
            } catch (Exception e) {
                System.err.println("Error del cliente: partidaLista");
                e.printStackTrace();
            }
            if (auxJugadoresVivos != jugadoresVivos) {
                System.out.println("\t" + jugadoresVivos + "/" + N + "  jugadores");   
                auxJugadoresVivos = jugadoresVivos;
            }
        }
    }

    public void asignarControles(){
        m = new MovimientoJugador(this.jugador.getId(),
            this.jugador.getX(), this.jugador.getY(), this.mapa.getRen(), 
            this.mapa.getCol(), this.stub);

        m.escucha();
    }

    public void actualizarObjetos(){
        //posible error: agregar try-catch
        InterfazEstadoPartida nuevoEstado = null;
        try {    
            nuevoEstado = stub.obtenerEstado();
        } catch (Exception e) {
            System.err.println("Error del cliente: obtenerEstado");
            e.printStackTrace();
        }

        this.listaJugadores = nuevoEstado.getListaJugadores();
        ArrayList<Bomba> nuevaListaBombas = nuevoEstado.getListaBombas();

        boolean agregarBomba = true;
        for (Bomba b : nuevaListaBombas) {
            agregarBomba = true;
            for (Bomba lb : listaBombas) {
                if (b.getIdBomba() == lb.getIdBomba()){
                    agregarBomba = false;
                }
            }
            if (agregarBomba){
                Bomba nb = new Bomba(b.getIdBomba(), b.getX(), b.getY(), b.getIdPropietario());
                // nb.setTicksParaExplotar(fps * 3);//3 seg para explotar
                nb.setTicksParaExplotar((1000 / fps) * 3);//3 seg para explotar
                // nb.setTicksParaExplotar(300);//3 seg para explotar
                listaBombas.add(nb);
            }
        }
    }

    public void actualizarMapa(){
        /**
         * Aqui se puede actualizar el timer de las bombas (cada : fps * 3 ticks)
         */
        // ifor (Bomba b : this.listaBombas) {
        for (int i = 0; i < listaBombas.size(); i++) {
            Bomba b = listaBombas.get(i);
            // System.out.println("aqui");
            b.setTickActual(b.getTickActual() + 1);
            // System.out.println("my tick: " + b.getTickActual());
            // System.out.println("my tick to ex: " + b.getTicksParaExplotar());
            if (b.getTickActual() >= b.getTicksParaExplotar()){
                // b.setEstadoBomba(true);
                /**
                 * VAlidar radio de la explosion y si el jugador murió
                 */
                //m.validarRadio(b.getX(),b.getY());
                if (b.getIdPropietario() == this.jugador.getId()){
                    try {
                        stub.quitarBomba(b.getIdBomba());//quitar bomba del servidor       
                    } catch (Exception e) {
                        System.err.println("Error del cliente: quitarBomba");
                        e.printStackTrace();
                    }
                }
                // listaBombas.remove(ib);
                listaBombas.remove(b);
            }
        }
        
        
        //recuperar mapa original
        mapaObjetos.setMapa(mapa.getMapa());
        // Agregar objetos al mapa
        for (Jugador j : listaJugadores) {
            if (j.getId() == this.jugador.getId()){
                mapaObjetos.asignaObjeto(j.getX(), j.getY(), mapaObjetos.JUGADOR);
            } else {
                mapaObjetos.asignaObjeto(j.getX(), j.getY(), mapaObjetos.JUGADOR_EXTERNO);
            }
        }
        
        for (Bomba b  : listaBombas) {
            mapaObjetos.asignaObjeto(b.getX(), b.getY(), mapaObjetos.BOMBA);
        }


        try {
            Thread.sleep((long) fps);
        } catch (Exception e) {
            System.out.println("Excepcion del cliente: fps");
            e.printStackTrace();
        }

        m.setMapa(mapaObjetos.getMapa());
    }

    public void dibujarMapa(){
        mapaObjetos.mostrarMapa();            
    }

    public void limpiarPantalla(){
        System.out.print("\033[H\033[2J"); // Limpiar pantalla
        // System.out.flush();
    }

}