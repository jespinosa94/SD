import java.lang.Exception;
import java.net.Socket;
import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

public class HiloController extends Thread {
	private Socket skCliente;
	private String ipRMI="";
	private String puertoRMI="";
	
	public HiloController(Socket p_cliente, String ipRMI, String puertoRMI) {
		this.skCliente = p_cliente;
		this.ipRMI = ipRMI;
		this.puertoRMI = puertoRMI;
	}
	
	public String leeSocket(Socket p_sk, String p_Datos) {
		try {
			InputStream aux = p_sk.getInputStream();
			BufferedReader flujo = new BufferedReader(new InputStreamReader(aux));
			p_Datos = flujo.readLine();
		} catch(Exception e) {
			System.out.println("Error: " + e.toString());
		}
		return p_Datos;
	}
	
	public void escribeSocket(Socket p_sk, String p_datos) {
		try {
			OutputStream aux = p_sk.getOutputStream();
			PrintWriter flujo = new PrintWriter(new OutputStreamWriter(aux));
//d			flujo.println("{" + p_Datos + "}");
			flujo.flush(); //limpiar buffer
		} catch(Exception e) {
			System.out.println("Error" + e.toString());
		}
	}
	
	@SuppressWarnings("deprecation")
	public void run() {
		InterfazController sensor = null;
		String Cadena = "";
		
		try {
			Cadena = this.leeSocket(skCliente, Cadena);
//d			System.out.println("Cadena que recibe el controller: " + Cadena); /* /controladorSD/volumen?Sonda=1 */
			System.setSecurityManager(new RMISecurityManager());
			String servidor = "rmi://" + ipRMI + ":" + puertoRMI;
			String[] sensores = Naming.list(servidor);
		} catch(Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}
}

































