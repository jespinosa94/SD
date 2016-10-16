import java.lang.Exception;
import java.net.Socket;
import java.io.*;

public class HiloServidor extends Thread {
	private Socket skCliente;
	private int conexionesActuales;

	public HiloServidor(Socket p_cliente, int conexionesActuales) {
		this.skCliente = p_cliente;
		this.conexionesActuales = conexionesActuales;
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

	public String procesaCadena(String Cadena) {
		return "";
	}

	public void run() {
		String Cadena = "", resultado = "";

		try {
			Cadena = this.leeSocket(skCliente, Cadena);
//d			System.out.println(Cadena);
			resultado = this.procesaCadena(Cadena);
			Cadena = "" + resultado;
			this.escribeSocket(skCliente, Cadena);
			skCliente.close();

		} catch(Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}

}