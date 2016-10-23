import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;
import java.rmi.*;
import java.rmi.server.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * Ruta de CLASSPATH Elementary:
 * /usr/lib/jvm/java-1.8.0-openjdk-amd64/bin/
 */

@SuppressWarnings("serial")
public class ObjetoSensor extends UnicastRemoteObject implements InterfazSensor, Serializable {
	
	private int volumen;
	private LocalDate fechaUltimoCambio;
	private int led;

	public ObjetoSensor(String id) throws RemoteException {
		super();
		leerSensor("/sensor" + id + ".txt");
	}

	public int GetVolumen() throws RemoteException {
		return volumen;
	}

	public LocalDate GetFechaActual() throws RemoteException {
		return LocalDate.now();
	}

	public LocalDate GetFechaUltimoCambio() throws RemoteException {
		return fechaUltimoCambio;
	}

	public int GetLED() throws RemoteException {
		return led;
	}

	public void SetLED(int valor, String nombreFichero) throws RemoteException {
		String valorAnterior = Integer.toString(led);
		led = valor;
		File fichero = new File(System.getProperty("user.dir") + nombreFichero);
		String lectura = "";	
		
		try {
	        FileReader fr = new FileReader(fichero);
	        BufferedReader br = new BufferedReader(fr);

	        String linea;
	        while((linea = br.readLine()) != null) {
	          if(linea.contains(valorAnterior)) {
	            linea = linea.replace(valorAnterior, Integer.toString(valor));
	          }
	          lectura += linea + ",";
	        }
	        br.close();
	        fr.close();
	        
	        String[] escritura = lectura.split(",");
	        FileWriter fw = new FileWriter(fichero, false);
	        for(String s: escritura) {
	          fw.write(s);
	          fw.write("\n");
	          fw.flush();
	        }

	        fw.close();
		} catch(Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}
	
	public String leerSensor(String s) throws RemoteException {
		String lectura = "";
		File fichero = new File(System.getProperty("user.dir") /*+ "//resources"*/ + s);
		
		try {
			FileReader fr = new FileReader(fichero);
			BufferedReader br = new BufferedReader(fr);
			String linea;
			while((linea = br.readLine()) != null) {
				if(linea.contains("Volumen")) {
					volumen = Integer.parseInt(linea.split("=")[1]);
				} else if(linea.contains("UltimaFecha")) {
					DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
					String fecha = linea.split("=")[1];
					fechaUltimoCambio = LocalDate.parse(fecha, formato);
				} else if(linea.contains("Led")) {
					led = Integer.parseInt(linea.split("=")[1]);
				}
				lectura += linea;
			}
			br.close();
			fr.close();
		} catch(Exception e) {
			/*
			 * Cambiar por error 404 not found o crearlo, no lo se aun
			 */
			System.out.println("Error: " + e.toString());
		}
		
		return lectura;
	}

}
