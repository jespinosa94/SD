package servidor;
import java.net.*;

/*
 * Método de llamada al servidor: [Puerto de escucha servidor], [IP del controlador], 
 * [Puerto del controlador], [Nº de conexiones simultaneas]
 */
public class ServidorConcurrente {
	/**
	 * @param args
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		String puerto = "";
		
		try {
			if(args.length == 1) {
				puerto = args[0];
				ServerSocket skServidor = new ServerSocket(Integer.parseInt(puerto));
				System.out.println("Servidor escuchando en el puerto " + puerto);
				/*
				 * Mantenimiento de la conexión con el cliente
				 */
				for(;;) {
					//Crea el objeto y espera a que el cliente crea conectarse
					Socket skCliente = skServidor.accept(); 
					System.out.println("Sirviendo al cliente...");
					Thread t = new HiloServidor(skCliente);
					t.start();
				}
			} else {
//				//Creación del recurso por defecto de index.html?
				System.out.println("Método de llamada al servidor: [Puerto de escucha servidor], [IP del controlador], [Puerto del controlador], [Nº de conexiones simultaneas]");
				System.exit(1);
			}
		} catch(Exception e){
			System.out.println("Error: " + e.toString());
		}
	}
}
