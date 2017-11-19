

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.List;


public class ClientImp extends UnicastRemoteObject implements InterfaceClient{

	public String contentPath = "/home/adria/rmi/content";

	public ClientImp() throws RemoteException{
		super();
	}

	public void sendMessage(String message) throws RemoteException{
		System.out.println(message);
	}

	public void uploadContent(byte[] file, String title, String description) throws RemoteException{
		
	}

}