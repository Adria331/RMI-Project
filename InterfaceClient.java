
import java.rmi.*;

public interface InterfaceClient extends Remote{
	public void sendMessage(String message) throws RemoteException;
	public void saveContentKey(String key) throws RemoteException;
	public void removeContentKey(String key) throws RemoteException;
}