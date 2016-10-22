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
		InterfazSensor sensor = null;
		String servidor = "rmi://" + ipRMI + ":" + puertoRMI + "/ObjetoSensor";
		String Cadena = "";
		String datosSocket = "";
		
		/*
		 * Instanciación de todos los objetos remotos conocidos en el registro
		 */
		/*try {
			System.setSecurityManager(new RMISecurityManager());
			String[] sensores = Naming.list(servidor);
		} catch(Exception e) {
			System.out.println("Error al instanciar los objetos remotos: " + e.toString());
			System.exit(0);
		}*/
		
		try {
			Cadena = this.leeSocket(skCliente, Cadena);
//d			System.out.println("Cadena que recibe el controller: " + Cadena); /* /controladorSD/volumen?Sonda=1 */			
			if(Cadena.contains("volumen")) {
				System.setSecurityManager(new RMISecurityManager());
				System.out.println("Pedido el volumen " + Cadena.split("\\?")[1].split("=")[1]);
				sensor = (InterfazSensor) Naming.lookup(servidor + Cadena.split("\\?")[1].split("=")[1]);	
				System.out.println("[[" + sensor.GetVolumen() + "]]");
				
				String cuerpo = "";
				cuerpo+="<hr> 	</hr> <h2> Sensor "+Cadena.split("\\?")[1].split("=")[1]+"</h2>";
                cuerpo+="<a href=\"volumen?station="+Cadena.split("\\?")[1].split("=")[1]+"\" class=\"btn btn-danger\">volumen</a>";
                cuerpo+="<a href=\"fecha?station="+Cadena.split("\\?")[1].split("=")[1]+"\" class=\"btn btn-primary\">fecha</a>";
                cuerpo+="<a href=\"ultimafecha?station="+Cadena.split("\\?")[1].split("=")[1]+"\" class=\"btn btn-success\">ultimafecha</a>";
                cuerpo+="<a href=\"luz?station="+Cadena.split("\\?")[1].split("=")[1]+"\" class=\"btn btn-warning\">luz</a>";
                
                datosSocket = cuerpo;
			}
			escribeSocket(skCliente, datosSocket);
			skCliente.close();
		} catch(Exception e) {
			System.out.println("Error accediendo al objeto remoto que buscas: " + e.toString());
		}
	}
}

































