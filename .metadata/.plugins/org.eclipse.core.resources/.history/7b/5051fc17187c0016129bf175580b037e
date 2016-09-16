package sockets_guiada;
import java.lang.Exception;
import java.net.Socket;
import java.io.*;

public class HiloServidor extends Thread {
	private Socket skCliente;
	
	/*
	 * Constructor con argumentos
	 */
	public HiloServidor(Socket p_cliente) {
		this.skCliente = p_cliente;
	}
	
	/*
	 * Devuelve el nº de bytes leidos
	 * 0 si se cierra el fichero
	 * -1 error
	 */
	public String leeSocket(Socket p_sk, String p_Datos) {
		try {
			InputStream aux = p_sk.getInputStream();
		} catch (Exception e) {
			System.out.println("Error" + e.toString());
		}
		return p_Datos;
	}
}
