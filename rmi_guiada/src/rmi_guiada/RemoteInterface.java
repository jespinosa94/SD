package rmi_guiada;
import java.rmi.Remote;


/*
 * Métodos que pueden ser invocados por el cliente
 */
public interface RemoteInterface extends Remote {
	public int suma(int a, int b) throws java.rmi.RemoteException;
	public int multiplicacion (int a, int b) throws java.rmi.RemoteException;
}
