import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.rmi.*;
import java.rmi.server.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("serial")
public class ObjetoSensor extends UnicastRemoteObject implements InterfazSensor, Serializable {
	
	private int volumen;
	private LocalDateTime fechaUltimoCambio;
	private int led;

	public ObjetoSensor(String id) throws RemoteException {
		super();
		leerSensor("sensor" + id + ".txt");
	}

	public int GetVolumen() throws RemoteException {
		return volumen;
	}

	public LocalDate GetFechaActual() throws RemoteException {
		return LocalDate.now();
	}

	public LocalDateTime GetFechaUltimoCambio() throws RemoteException {
		return fechaUltimoCambio;
	}

	public int GetLED() throws RemoteException {
		return led;
	}

	public void SetLED(int valor) {
		led = valor;
	}
	
	public String leerSensor(String s) throws RemoteException {
		String lectura = "";
		File fichero = new File(System.getProperty("user.dir") + "//resources" + s);
		
		try {
			FileReader fr = new FileReader(fichero);
			BufferedReader br = new BufferedReader(fr);
			String linea;
			while((linea = br.readLine()) != null) {
				if(linea.contains("Volumen")) {
					volumen = Integer.parseInt(linea.split("=")[1]);
				} else if(linea.contains("UltimaFecha")) {
					DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/YYYY hh:mm:ss");
					String fecha = linea.split("=")[1];
					fechaUltimoCambio = LocalDateTime.parse(fecha, formato);
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
