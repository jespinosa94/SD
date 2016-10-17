import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Time;

public interface InterfazController extends Remote {
	public int GetVolumen() throws RemoteException; //Entre 0 y 100
	public Time GetFechaActual() throws RemoteException; //fecha y hora interna del sistema
	public Time GetFechaUltimoCambio() throws RemoteException;
	public int GetLED() throws RemoteException;
	public void SetLED(int valor);
	public String procesaSensor(String s) throws RemoteException;
}
