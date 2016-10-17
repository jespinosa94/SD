import java.net.*;

/*
 * Extiende la funcionalidad del HTTPServer e implementa la llamada a recursos dinamicos en forma de aplicaciones
 */
public class ServidorController {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		String puerto="";

		try
		{
			if(args.length == 3) {	
				puerto = args[0];
				ServerSocket skServidor = new ServerSocket(Integer.parseInt(puerto));
				System.out.println("Controlador escuchando en el puerto: " + puerto);
				
				for(;;) {
					Socket skCliente = skServidor.accept();
					System.out.println("El controlador está sirviendo al cliente, espera un momento...");
					Thread t = new HiloController(skCliente, args[1], args[2]);
					t.start();
				}
			} else {
				System.out.println("Error en los argumentos del controlador, comprueba que son: [Puerto de escucha] [IP RMI] [PuertoRMI]");
			}
		} catch(Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}
}
