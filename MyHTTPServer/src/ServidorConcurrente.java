import java.net.*;
import java.util.Set;

/*
 * Metodo de llamada al servidor: [Puerto de escucha servidor], [IP del controlador],
 * [Puerto del controlador], [Num de conexiones simultaneas]
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
			if(args.length == 4) {
				//Conexiones con el hilo
				puerto = args[0];
				conexionesMax = Integer.parseInt(args[3]);
				ServerSocket skServidor = new ServerSocket(Integer.parseInt(puerto));
				System.out.println("Portal abierto: Escuchando en el puerto: " + puerto);

				//Comunicacion con el cliente
				for(;;) {
					//A la espera de que un cliente quiera conectarse
					if(conexionesActuales <= conexionesMax) {
						Socket skCliente = skServidor.accept();
						System.out.println("El portal ha sido atravesado: Sirviendo al cliente...");
						Thread t = new HiloServidor(skCliente, conexionesActuales);
						t.start();
						conexionesActuales += 1;
//d						System.out.println("[" + conexionesActuales + "]");
					} else {
						System.out.println("Error: connection refused, intente conectar de nuevo en unos instantes");
						throw new ConnectException();
					}
				}
			} else {
				System.out.println("Debe indicar el puerto de escucha del servidor,"
									+ " IP del controlador,"
									+ " puerto de escucha del controlador y"
									+ " el numero de conexiones simultaneas permitidas"
									);
				System.out.println("Formato: $./Servidor [puerto_servidor] [IP del controlador] [Puerto de escucha del controlador] [num_conexiones]");
				System.exit(1);
			}
		} catch(Exception e){
			System.out.println("Error: " + e.toString());
		}
	}
}
