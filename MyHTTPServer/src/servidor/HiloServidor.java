package servidor;
import java.lang.Exception;
import java.net.Socket;
import java.io.*;

public class HiloServidor extends Thread {
	private Socket skCliente;
	
	public HiloServidor(Socket p_cliente) {
		this.skCliente = p_cliente;
	}
	
	public String leeSocket(Socket p_sk, String p_Datos) {
		try {
			//Lectura caracter a caracter
			InputStream aux = p_sk.getInputStream();
			BufferedReader flujo = new BufferedReader(new InputStreamReader(aux));
//			//Quitar si no funciona
			p_Datos = new String();
			p_Datos = flujo.readLine();
		} catch(Exception e) {
			System.out.println("Error: " + e.toString());
		}
		return p_Datos;
	}
	
	public void escribeSocket(Socket p_sk, String p_Datos) {
		try {
			
		} catch(Exception e) {
			System.out.println("Error: " + e.toString());
		}
		//Si no funciona...
//		//return;
	}

}
