import java.lang.Exception;
import java.net.Socket;
import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

public class HiloController extends Thread {
	private Socket skCliente;
	private String ipRMI="";
	private String puertoRMI="";
	private String atributoSensor = "";
	private String idSensor = "";
	
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
	
	public void escribeSocket(Socket p_sk, String p_Datos) {
		try {
			OutputStream aux = p_sk.getOutputStream();
			PrintWriter flujo = new PrintWriter(new OutputStreamWriter(aux));
			flujo.println(p_Datos);
			flujo.flush(); //Limpieza del Buffer
		} catch(Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}
	
	public String leerArchivo(String s) {
		String lectura = "";
		File fichero = new File(System.getProperty("user.dir") + s); //Obtiene la ruta del fichero
		try {
			FileReader fr = new FileReader(fichero);
			BufferedReader br = new BufferedReader(fr);
			String linea;
			while((linea = br.readLine()) != null) {
				lectura += linea;
			}
			fr.close();
			br.close();
		} catch(Exception e) {
			lectura = "Error 404: fichero no encontrado";
		}
		
		return lectura;
	}
	
	public String procesaPeticionRMI(InterfazSensor sensor, String servidor) {
		String respuesta = "";
		if(atributoSensor.contains("volumen")) {
			System.setSecurityManager(new RMISecurityManager());
			System.out.println("Controller pide el volumen " + "del sensor " + idSensor);
				
//d				System.out.println("[" + sensor.GetVolumen() + "]");
			
			String cuerpo = "";
			cuerpo = leerArchivo("/index.html");
			try {
				if(cuerpo.contains("404")) {
					System.out.println("Error al crear la página HTML del recurso pedido");
				} else {
					cuerpo+="<h1> Sensor " + idSensor + "</h1> </div>";
					cuerpo+="<div class=\"color1 col-md-9\"> <h2> Volumen = " + sensor.GetVolumen() + "</h2> </div> </div> </div> </body> </html>";
				}	
			} catch(Exception e) {
				System.out.println(e.toString());
			}
			respuesta = cuerpo;
		}
		return respuesta;
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
			//Desencapsulación de la información recibida por HttpServer
			atributoSensor = Cadena.split(" ")[0];
			idSensor = Cadena.split(" ")[1];
			System.out.println("Valores que recibe el controller: " + "[Atributo = " + atributoSensor + "], [id = " + idSensor + "]"); /* /controladorSD/volumen?Sonda=1 */
			
			sensor = (InterfazSensor) Naming.lookup(servidor + idSensor);			
			datosSocket = procesaPeticionRMI(sensor, servidor);
			/*if(atributoSensor.contains("volumen")) {
				System.setSecurityManager(new RMISecurityManager());
				System.out.println("Controller pide el volumen " + "del sensor " + idSensor);
				sensor = (InterfazSensor) Naming.lookup(servidor + idSensor);	
//d				System.out.println("[" + sensor.GetVolumen() + "]");
				
				String cuerpo = "";
				cuerpo = leerArchivo("/index.html");
				if(cuerpo.contains("404")) {
					System.out.println("Error al crear la página HTML del recurso pedido");
				} else {
					cuerpo+="<h1> Sensor " + idSensor + "</h1> </div>";
					cuerpo+="<div class=\"color1 col-md-9\"> <h2> Volumen = " + sensor.GetVolumen() + "</h2> </div> </div> </div> </body> </html>";
				}	
				datosSocket = cuerpo;
			}*/
//d			System.out.print(datosSocket);
			escribeSocket(skCliente, datosSocket);
			skCliente.close();
		} catch(Exception e) {
			System.out.println("Error accediendo al objeto remoto que buscas: " + e.toString());
		}
	}
}

































