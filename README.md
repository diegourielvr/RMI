Implementacion del juego "Bomberman" con RMI.

Generar binarios para el servidor:
(1) Ubicarse en la carpeta ./bomberman_servidor/
(2) Crear carpeta "bin" (cmd: mkdir bin)
(3) Desde la carpeta "./bomberman_servidor/" compilar con:

javac -d ./bin/ Bomberman.java BombermanServidor.java EstadoPartida.java InformacionInicial.java InterfazEstadoPartida.java InterfazInformacionInicial.java InterfazJugador.java InterfazMapa.java InterfazPosicion.java Jugador.java Mapa.java Posicion.java

Generar binarios para el cliente:
(1) Ubicarse en la carpeta ./bomberman_cliente/
(2) Crear carpeta "bin" (cmd: mkdir bin)
(3) Desde la carpeta "./bomberman_cliente/" compilar con:
javac -d ./bin/ Bomberman.java BombermanCliente.java InterfazEstadoPartida.java InterfazInformacionInicial.java InterfazJugador.java InterfazMapa.java InterfazJugador.java InterfazPosicion.java

Ejecutar el servicio rmiregistry desde ./bomberman_servidor/bin/ 

Ejecutar el servidor 
bomberman_servidor/bin $: java -Djava.rmi.server.codebase=file: BombermanServidor

Ejecutar el cliente
bomberman_cliente $: java -cp ./bin/ BombermanCliente