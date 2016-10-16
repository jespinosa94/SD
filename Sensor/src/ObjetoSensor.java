import java.io.Serializable;
import java.rmi.*;
import java.rmi.server.*;
import java.sql.Time;

@SuppressWarnings("serial")
public class ObjetoSensor extends UnicastRemoteObject implements InterfazSensor, Serializable {
	
	private int volumen;
	private Time fechaActual;
	private Time fechaUltimoCambio;
	private int luzLED;

	public ObjetoSensor() throws RemoteException {
		super();
	}

	public int GetVolumen() throws RemoteException {
		return volumen;
	}

	public Time GetFechaActual() throws RemoteException {
		return fechaActual;
	}

	public Time GetFechaUltimoCambio() throws RemoteException {
		return fechaUltimoCambio;
	}

	public int GetLED() throws RemoteException {
		return luzLED;
	}

	public void SetLED(int valor) {
		luzLED = valor;
	}
	
	public String procesaSensor(String s) throws RemoteException {
		String resultado = "";
		
		return resultado;
	}

}