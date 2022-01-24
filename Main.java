import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String host = (args.length < 1) ? null : args[0];

        System.out.println("🌀🌀 BOMBERMAN GAME 🌀🌀");

        // Localizar Objeto Remoto
        // InterfazBomberman Stub = null;
        // try {
        //     Registry registry = LocateRegistry.getRegistry(host);
        //     Stub = (InterfazBomberman) registry.lookup("Bomberman");
        // } catch (Exception e) {
        //     System.err.println("Error al localizar Objeto Remoto");
        //     e.printStackTrace();
        // }
        
        BombermanCliente cliente = new BombermanCliente();
        // cliente.asociarStub(Stub);
        cliente.localizarObjetoRemoto("Bomberman", host); 
        System.out.println("Número de jugadores (Entre 2 y 4): ");
        int N = sc.nextInt();
        if (cliente.buscarPartida(N)){
            System.out.println("Se ha creado una nueva partida!");
        } else {
            System.out.println("Hay una partida creada!");   
        }

        System.out.println("Frecuencia de actualizacion: ");
        int fps = sc.nextInt();
        cliente.setFrecuencia(fps);

        System.out.println("Ingresa tu apodo: ");
        String nombre = sc.next();

        if (!cliente.unirsePartida(nombre)){
            System.out.println("La partida se encuentra llena");
            System.out.println("Intenta más tarde!");
            System.exit(0);
        }
        System.out.println("Te uniste a una partida!");
        
        sc.close();
        cliente.limpiarPantalla();

        System.out.println("\tEsperando jugadores");
        cliente.esperarJugadores();

        cliente.asignarControles();

        boolean corriendoPartida = true;

        while(corriendoPartida){
            cliente.actualizarEntidades();
            cliente.actualizarMapa();
            cliente.limpiarPantalla();
            cliente.dibujarMapa();
            corriendoPartida = cliente.getEstadoPartida(); 
        }

        System.out.println("\tEl juego terminó!");
        try {
            Thread.sleep(1000);
        } catch (Exception e) {}
        cliente.limpiarPantalla();

        if (cliente.getEstadoJugador()){
            System.out.println("\t\tFelicidades!!");
            System.out.println("\tEres el ganador");
        } else {
            System.out.println("\tSuerte para la proxima!!"); 
        }
        System.exit(0);
    }
}
