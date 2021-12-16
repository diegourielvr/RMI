import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class BombermanCliente {

    public BombermanCliente(){}

    public static void main(String[] args) {
        int N = 2; // Número de jugadores
        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);// null indica por defecto 'localhost'
            Bomberman stub = (Bomberman) registry.lookup("Bomberman");// Localizar servicio
            System.out.println("[cliente]: Creando una nueva partida...");
            boolean initPartida = stub.nuevaPartida(N);
            System.out.println("Estado de partida: " + initPartida);
        } catch (Exception e) {
            System.err.println("Excepcion del Cliente: " + e.toString());
            e.printStackTrace();
        }

    }

}