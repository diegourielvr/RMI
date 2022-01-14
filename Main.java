import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String host = (args.length < 1) ? null : args[0];

        System.out.println("🌀🌀 BOMBERMAN GAME 🌀🌀");

        // Localizar Objeto Remoto
        InterfazBomberman Stub = null;
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            Stub = (InterfazBomberman) registry.lookup("Bomberman");
        } catch (Exception e) {
            System.err.println("Error al localizar Objeto Remoto");
            e.printStackTrace();
        }
        
        BombermanCliente cliente = new BombermanCliente();
        cliente.asociarStub(Stub);
        
        System.out.println("Número de jugadores (Entre 2 y 4): ");
        int N = sc.nextInt();
        if (cliente.buscarPartida(N)){
            System.out.println("Se ha creado una nueva partida!");
        } else {
            System.out.println("Hay una partida creada!");   
        }

        System.out.println("Frecuencia de actualizacion: ");
        int frecuencia = sc.nextInt();
        cliente.setFrecuencia(frecuencia);

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
        int ticks = 0;

        while(corriendoPartida){
            
            // obtener listas de jugadores y bombas
            cliente.actualizarObjetos();
            
            //Pendiente: llevar un conteo de ticks en las bombas
            cliente.actualizarMapa();
            
            cliente.limpiarPantalla();

            cliente.dibujarMapa();
            

            ticks++;
            if (ticks >= 1000/frecuencia){
                ticks = 0;
            }
            //System.out.println("Ticks: " + ticks);
        }
    }
}
