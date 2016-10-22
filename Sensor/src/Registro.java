import java.rmi.*;

public class Registro {
	
	public static void main(String args[]) {
		String URLRegistro;
		try {
			System.setSecurityManager(new RMISecurityManager());
			ObjetoSensor objetoSensor = new ObjetoSensor(args[0]);
			System.out.println("Sensor" + args[0] + " creado"); 
			URLRegistro = "/ObjetoSensor" + args[0];
			Naming.rebind(URLRegistro, objetoSensor);
			System.out.println("Servidor de objeto preparado.");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
