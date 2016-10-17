import java.lang.Exception;
import java.net.Socket;
import java.io.*;

public class HiloController extends Thread {
	private Socket skCliente;
	private String IpRMI;
	private String PuertoRMI;
	
	public HiloController(Socket p_cliente, String p_IpRMI, String p_PuertoRMI) {
		this.skCliente = p_cliente;
		this.IpRMI = p_IpRMI;
		this.PuertoRMI = p_PuertoRMI;
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
//d			flujo.println(p_Datos);
			flujo.flush(); //limpiar buffer
		} catch(Exception e) {
			System.out.println("Error" + e.toString());
		}
	}
	
	public void run() {
		InterfazController sensor = null;
		String Cadena = "";
		
		try {
			Cadena = this.leeSocket(skCliente, Cadena);
			System.out.println("Cadena que recibe el controller: " + Cadena);
		} catch(Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}
}

































