

import java.rmi.*;


public class ClientImp extends UnicastRemoteObject implements ClientInterface{

	public String contentPath = "/home/adria/rmi/content";

	public ClientImp() throws RemoteException{

	}

	@Override
	public void sendMessage(String message) throws RemoteException{

	}
	
	@Override
	public void sendContent(byte[] file, String title, String description) throws RemoteException{
		
	}

}