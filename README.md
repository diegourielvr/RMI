# Implementacion del juego **Bomberman** con RMI
-----
## Generar binarios para el servidor:
* Crear carpeta **bin/** dentro de la carpeta **RMI/** 
    * RMI$: ```mkdir bin```
* Desde la carpeta **RMI/** compilar con:
    ```
    javac -d ./bin/ Informacion.java Jugador.java BombermanCliente.java InterfazBomberman.java Mapa.java BombermanServidor.java InterfazEstadoPartida.java Posicion.java EstadoPartida.java InterfazInformacion.java MovimientoJugador.java
    ```
## Ejecutar servicio *+rmiregistry**
* RMI/bin$: rmiregistry

## Ejecutar servidor 
* RMI/bin$: ```java -cp ./bin/ -Djava.rmi.server.codebase=file:./bin/ BombermanServidor```

## Ejecutar cliente
* RMI/bin$: ```java -cp ./bin/ BombermanCliente```

