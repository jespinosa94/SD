package sockets_guiada;
import java.io.*;
import java.net.*;

public class Cliente {
	public String leeSocket (Socket p_sk, String p_Datos) {
		try {
			InputStream aux = p_sk.getInputStream();
			DataInputStream flujo = new DataInputStream(aux);
			p_Datos = flujo.readUTF();
		} catch(Exception e) {
			System.out.println("Error: " + e.toString());
		}
		return p_Datos;
	}
	
	public void escribeSocket (Socket p_sk, String p_Datos) {
		try {
			OutputStream aux = p_sk.getOutputStream();
			DataOutputStream flujo = new DataOutputStream(aux);
			flujo.writeUTF(p_Datos);
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}
	
	public String pedirMarca(int anyadeMarca, String p_resultado, String p_Cadena, Socket p_socket_Con_Servidor) {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		int op = 0;
		
		try {
			System.out.println("Introduzca la marca: ");
			op = Integer.parseInt(br.readLine());
			
			p_Cadena = anyadeMarca + ", " + op;
			escribeSocket(p_socket_Con_Servidor, p_Cadena);
			p_Cadena = "";
			p_Cadena = leeSocket(p_socket_Con_Servidor, p_Cadena);
			p_resultado = p_Cadena;
			
		} catch(Exception e) {
			System.out.println("Error: " + e.toString());
		}
		
		return p_resultado;
	}
	
	public void pedirMarca(String p_host, String p_puerto) {
		int anyadeMarca;
		int salir = 0;
		String resultado = "";
		char resp = 'x';
		String Cadena = "";
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		try {
			Socket skCliente = new Socket(p_host, Integer.parseInt(p_puerto));
			while(salir == 0) {
				System.out.println("[1] Ferrari");
				System.out.println("[2] Tesla");
				System.out.println("Opcion: ");
				anyadeMarca = Integer.parseInt(br.readLine());
				resultado = pedirMarca(anyadeMarca, resultado, Cadena, skCliente);
				
				while (resp != 's' && resp != 'n') {
					System.out.println("El resultado es: " + resultado);
					System.out.println("Desea realizar otra operacion? ");
					resp = br.readLine().charAt(0);
				}
				
				if(resp != 's') {
					salir = 1;
					
					escribeSocket(skCliente, "fin");
					
					Cadena = leeSocket(skCliente, Cadena);
					resultado = Cadena;
					
					if (resultado == "-1") {
						skCliente.close();
						System.out.println("Conexion cerrada");
						System.exit(0);
					}
				}
			}
		} catch(Exception e) {
			System.out.println("Error: " + e.toString());
		}
		
	}
	
	public void menu(String p_host, String p_puerto) {
		int opcion = 0;
		try {
			while(opcion != 1 && opcion != 2) {
				System.out.println("[1] Enviar marca de coche");
				System.out.println("[2] Salir");
				System.out.print("Opcion: ");
				InputStreamReader isr = new InputStreamReader(System.in);
				BufferedReader br = new BufferedReader(isr);
				opcion = Integer.parseInt(br.readLine());
				
				if(opcion == 1) {
					pedirMarca(p_host, p_puerto);
				} else {
					System.exit(0);
				}
						
			}
		} catch(Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}
	
	public static void main(String[] args) {
		Cliente cl = new Cliente();
		int i = 0;
		String host;
		String puerto;
		if (args.length < 2) {
			System.out.println("Se debe indicar la direccion del servidor y el puerto.");
			System.exit(-1);
		}
		host = args[0];
		puerto = args[1];
		
		while(i == 0) {
			cl.menu(host, puerto);
		}
	}
}
