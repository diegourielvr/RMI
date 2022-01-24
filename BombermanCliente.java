import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class BombermanCliente implements InterfazMapa{
    private int N; 
    private int jugadoresVivos;
    private long tmpFrecuencia; // Frecuancia a la que se actualiza el mapa
    private Mapa mapa; // Mapa original (con paredes)
    private Mapa mapaEntidades; // Mapa con entidades (jugadores y bomba)
    private Jugador jugador;
    private ControlJugador control;
    private InterfazBomberman stub;
    private ArrayList<Bomba> listaBombas;
    private ArrayList<Jugador> listaJugadores;
    private boolean partidaCorriendo;

    public BombermanCliente() {
        this.N = 0;
        this.stub = null;
        this.control = null;
        this.tmpFrecuencia = 0;
        this.jugadoresVivos = 0;
        this.partidaCorriendo = false;
        this.mapa = new Mapa();
        this.jugador = new Jugador();
        this.mapaEntidades = new Mapa();
        this.listaBombas = new ArrayList<Bomba>();
        this.listaJugadores = new ArrayList<Jugador>();
    }

    public void localizarObjetoRemoto(String nombre_obj, String host) {
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            stub = (InterfazBomberman) registry.lookup(nombre_obj);
        } catch (Exception e) {
            System.err.println("Error al localizar Objeto Remoto");
            e.printStackTrace();
        }
    }

    public void asociarStub(InterfazBomberman Stub){
        this.stub = Stub;
    }

    /** 
     * Busca una partida, si no existe
     * crea una partida para N jugadores 
     */
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

    /**
     * Frecuencia a la que se actulizan las entidades
     */
    public void setFrecuencia(int fps){
        this.tmpFrecuencia =  (long) (((double)MS / (double) fps) + 0.5);
        System.out.println("tiempo de frecuencia: "+ tmpFrecuencia + "ms");
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
            this.jugador.setEstado(true);// Jugador vivo
            this.jugador.setId(info.getId());
            this.jugador.setX(info.getPosX());//pos inicial x
            this.jugador.setY(info.getPosY());//pos inicial y
            this.mapa.setAlto(info.getAlto());
            this.mapa.setAncho(info.getAncho());
            this.mapa.initMapa();
            this.mapa.setMapa(info.getMapa());
            this.N = info.getN();// Numero de jugadores
            this.mapaEntidades.setAlto(info.getAlto());
            this.mapaEntidades.setAncho(info.getAncho());
            this.mapaEntidades.initMapa();
            this.partidaCorriendo = true;
        }
        return this.partidaCorriendo;
    }

    public void esperarJugadores(){
        int auxJugadoresVivos = 0;
        while (jugadoresVivos < N){
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
        control = new ControlJugador(this.jugador.getId(), this.jugador.getX(), 
                                    this.jugador.getY(), this.stub);
        control.escucha(this.jugador.getNombre());
    }

    /**
     * Llamada remota al servidor para obtener estado
     */
    public void actualizarEntidades(){
        InterfazEstadoPartida nuevoEstado = null;
        try {    
            nuevoEstado = stub.obtenerEstado();
        } catch (Exception e) {
            System.err.println("Error del cliente: obtenerEstado");
            e.printStackTrace();
        }

        // Actualizar posiciones de jugadores
        this.listaJugadores = nuevoEstado.getListaJugadores();
        this.jugadoresVivos = listaJugadores.size();
        if (jugadoresVivos == 1){ // Deshabilitar bombas
            this.partidaCorriendo = false;
            for (Bomba b : listaBombas) {
                b.setEstadoBomba(true);
            }
        }

        // Agregar solo las bombas que no estan en nuestra lista
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
                nb.setTicksParaExplotar((int)(MS / tmpFrecuencia) * TMP_EXPLOSION);//3 seg para explotar
                // nb.setTicksParaExplotar(300);//3 seg para explotar
                listaBombas.add(nb);
            }
        }
    }

    /**
     * Actualizar el mapa de entidades
     */
    public void actualizarMapa(){
        for (int i = 0; i < listaBombas.size(); i++) {
            Bomba b = listaBombas.get(i);
            b.setTickActual(b.getTickActual() + 1);
            if (b.getEstadoBomba() == false && b.getTickActual() >= b.getTicksParaExplotar()){
                b.setEstadoBomba(true);
                // Si un jugador es alcanzado por el radio de la bobma, lo eliminamos de la lista
                calculaRadio(b);
                //  Quitamos del servido las bombas que son nuestras
                if (b.getIdPropietario() == this.jugador.getId()){
                    try {
                        stub.quitarBomba(b.getIdBomba());     
                    } catch (Exception e) {
                        System.err.println("Error del cliente: quitarBomba");
                        e.printStackTrace();
                    }
                }
            }
            /*
             * Eliminar la bomba (local) 1 segundo despues de haber explotado,
             * para que al propietario le de tiempo de quitarla del servidor
             * y los demás no agreguen 2 veces la misma bomba
             */
            if (b.getEstadoBomba() == true && b.getTickActual() >= (b.getTicksParaExplotar() + (MS / tmpFrecuencia))){
                listaBombas.remove(b);
            }
        }
        
        // Recuperar mapa original
        mapaEntidades.setMapa(mapa.getMapa());

        for (Jugador j : listaJugadores) {
            if (j.getId() == this.jugador.getId())
                mapaEntidades.agregarEntidad(j.getX(), j.getY(), JUGADOR);
            else 
                mapaEntidades.agregarEntidad(j.getX(), j.getY(), ENEMIGO);
        }
        
        for (Bomba b  : listaBombas) {
            if (b.getEstadoBomba() == false) // Bomba activa | aun no explota
                mapaEntidades.agregarEntidad(b.getX(), b.getY(), BOMBA);
        }

        try {
            Thread.sleep((long) tmpFrecuencia);
        } catch (Exception e) {
            System.out.println("Excepcion del cliente: tmpFrecuencia");
            e.printStackTrace();
        }
        control.sincronizarMapa(mapaEntidades.getMapa());
    }

    // Verifica si un jugador se encontraba cerca de la bomba
    private void calculaRadio(Bomba b) {
        boolean eliminarJugador = false;
        eliminarJugador = mapaEntidades.jugadorEnRadio(b.getX(), b.getY(), RADIO);

        if (eliminarJugador) {
            this.jugador.setEstado(false);
            this.partidaCorriendo = false;
            try {
                stub.eliminacion(this.jugador.getId());
            } catch (Exception e) {
                System.out.println("Excepcion del cliente: eliminacion");
                e.printStackTrace();
            }
        }
    }
   
    public boolean getEstadoPartida() {
        return partidaCorriendo;
    }

    public boolean getEstadoJugador(){
        return this.jugador.getEstado();
    }

    public void dibujarMapa(){
        mapaEntidades.mostrarMapa();            
    }

    public void limpiarPantalla(){
        System.out.print("\033[H\033[2J"); // Limpiar pantalla
    }

}