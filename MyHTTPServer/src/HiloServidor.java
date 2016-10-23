import java.lang.Exception;
import java.net.Socket;
import java.io.*;

public class HiloServidor extends Thread {
	private Socket skCliente;
	private String IpController;
	private int PuertoController;
	
	public HiloServidor(Socket p_cliente, String IpController, String PuertoController) {
		this.skCliente = p_cliente;
		this.IpController = IpController;
		this.PuertoController = Integer.parseInt(PuertoController);
	}


	public String leeSocket(Socket p_sk, String p_Datos) {
		try {
			//Lectura caracter a caracter
			InputStream aux = p_sk.getInputStream();
			BufferedReader flujo = new BufferedReader(new InputStreamReader(aux));
			p_Datos = new String();
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
		return;
	}
	
	private String ConectaController(String peticionController) {
		/*
		 * Recibe cadena en la forma: "/controladorSD/volumen?Sonda=1"
		 * hay que procesarla y conectar con el controlador
		 */
		String resultado = "";
		try {
			Socket skControlador = new Socket(IpController, PuertoController);
			escribeSocket(skControlador, peticionController);
			resultado = leeSocket(skControlador, peticionController);
			skControlador.close();
		} catch(Exception e) {
			System.out.println("Error: " + e.toString());
		}

		return resultado;
	}
	
	public String leerArchivo(String s) {
		String lectura = "";
		File fichero = new File(System.getProperty("user.dir") + "//resources" + s); //Obtiene la ruta del fichero
		try {
			FileReader fr = new FileReader(fichero);
			BufferedReader br = new BufferedReader(fr);
			String linea;
			while((linea = br.readLine()) != null) {
				lectura += linea;
			}
			fr.close();
			br.close();
			String aux = lectura;
			lectura = "HTTP/1.1 200 OKi\r\n" + "Content-Type: text/html\r\n" + "\r\n\r\n" + aux + "\r\n";
		} catch(Exception e) {
			lectura = "HTTP/1.1 404 File not found\r\n" + "Content-Type: text/html\r\n" + "\r\n\r\n" + leerArchivo("/404.html").substring(45); //substring quita la cadena de encontrado del archivo 404
		}
		
		return lectura;
	}
	
	public String procesaCadena(String Cadena) {
		String[] s = Cadena.split(" ");
		String resultado = "";
		
		if (s[0].equals("GET")) {
//d			System.out.println("(" + s[1].toString() + ")");
			if(s[1].contains("controladorSD")) {
				String encapsulacion;
				encapsulacion = s[1].split("\\?")[0].split("\\/")[2]; //Atributo de la sonda
				encapsulacion += " " + s[1].split("\\?")[1].split("=")[1]; //id de la sonda
				resultado = ConectaController(encapsulacion);
			} 
			else if(s[1].equals("/")){	//La llamada con unicamente puerto e ip produce esta salida
				resultado = leerArchivo("/index.html");
			} 
			else {	//lectura de cualquier otro archivo
				resultado = leerArchivo(s[1]) ;
			}
		} else {
			try {
				this.escribeSocket(skCliente, "HTTP/1.1 405 Method Not Allowed\r\n" + "Content-Type: text/html\r\n" + "\r\n\r\n" + leerArchivo("/405.html").substring(45));
				skCliente.close();
			} catch(Exception e) {
				System.out.println("Error: " + e.toString());
			}
		}
		
		
		return resultado;
	}


	public void run() {
		String Cadena = "", resultado = "";
		try {
			Cadena = this.leeSocket(skCliente, Cadena);
//d			System.out.println("[" + Cadena + "]");
			resultado = this.procesaCadena(Cadena);
			Cadena = "" + resultado;
			this.escribeSocket(skCliente, Cadena);
			skCliente.close();

		} catch(Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}

}
