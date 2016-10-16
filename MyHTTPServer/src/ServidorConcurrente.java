import java.net.*;
import java.util.Set;

/*
 * M�todo de llamada al servidor: [Puerto de escucha servidor], [IP del controlador],
 * [Puerto del controlador], [N� de conexiones simultaneas]
 */
public class ServidorConcurrente {
	/**
	 * @param args
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) throws ConnectException {
		String puerto = "";
		int conexionesMax, conexionesActuales = 0;

		try {
			if(args.length == 2) {
				//Conexiones con el hilo
				puerto = args[0];
				conexionesMax = Integer.parseInt(args[1]);
				ServerSocket skServidor = new ServerSocket(Integer.parseInt(puerto));
				System.out.println("Portal abierto: Escuchando en el puerto: " + puerto);

				//Comunicaci�n con el cliente
				for(;;) {
					//A la espera de que un cliente quiera conectarse
					if(conexionesActuales <= conexionesMax) {
						Socket skCliente = skServidor.accept();
						System.out.println("El portal ha sido atravesado: Sirviendo al cliente...");
						Thread t = new HiloServidor(skCliente, conexionesActuales);
						t.start();
						conexionesActuales += 1;
						System.out.println("[" + conexionesActuales + "]");
					} else {
						System.out.println("Error: connection refused, intente conectar de nuevo en unos instantes");
						throw new ConnectException();
					}
				}
			} else {
				System.out.println("Debe indicar el puerto de escucha del servidor y"
									+ " el numero de conexiones simultaneas permitidas");
				System.out.println("Formato: $./Servidor [puerto_servidor] [n�_conexiones]");
				System.exit(1);
			}
		} catch(Exception e){
			System.out.println("Error: " + e.toString());
		}
	}
}