package sockets_guiada;
import java.net.*;
/*
 * logica de conexion y recepcion de peticiones
 */
public class ServidorConcurrente {
	/*
	 * @param args
	 */
	
	public static void main(String[] args) {
		String puerto = "";
		
		try {
			if (args.length < 1) {
				System.out.println("Se debe indicar el puerto de escucha del servidor");
				System.out.println("$./Servidor puerto_servidor");
				System.exit(1);
			}
			puerto = args[0];
			System.out.print(puerto);
			ServerSocket skServidor = new ServerSocket(Integer.parseInt(puerto));
			/*
			 * Mantener la comunicacion con el cliente
			 */
			for(;;) {
				//Espera a que el cliente conecte
				Socket skCliente = skServidor.accept(); //Creacion de objeto
				System.out.println("Sirviendo al cliente...");
				Thread t = new HiloServidor(skCliente);
				t.start();
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}
}