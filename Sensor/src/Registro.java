import java.rmi.*;

public class Registro {
	
	public static void main(String args[]) {
		String URLRegistro;
		try {
			System.setSecurityManager(new RMISecurityManager());
			ObjetoSensor objetoSensor = new ObjetoSensor();
			URLRegistro = "/ObjetoSensor";
			Naming.rebind(URLRegistro, objetoSensor);
			System.out.println("Servidor de objeto preparado.");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
