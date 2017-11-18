
import java.rmi.*;

public interface InterfaceClient extends Remote{
	public void sendMessage(String message) throws RemoteException;
	public void uploadContent(byte[] file, String title, String description) throws RemoteException;
}