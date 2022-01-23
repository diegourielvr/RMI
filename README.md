# Implementacion del juego *Bomberman* con RMI
-----
## Generar binarios:
* Crear carpeta **bin/** dentro de la carpeta **RMI/** 
    * RMI$: 
    ```
    mkdir bin
    ```
* Desde la carpeta **RMI/** compilar con:
    ```
    javac -d ./bin/ Informacion.java Jugador.java BombermanCliente.java InterfazBomberman.java Mapa.java BombermanServidor.java InterfazEstadoPartida.java InterfazJugador.java Posicion.java EstadoPartida.java InterfazInformacion.java ControlJugador.java Bomba.java InterfazBomba.java Main.java
    ```
## Ejecutar servicio *rmiregistry*
* RMI/bin$: 
    ```
    rmiregistry
    ```
## Ejecutar servidor 
* RMI$: 
    ```
    java -cp ./bin/ -Djava.rmi.server.codebase=file:./bin/ BombermanServidor
    ```

## Ejecutar cliente
* RMI$: 
    ```
    java -cp ./bin/ Main
    ```

