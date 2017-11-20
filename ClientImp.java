

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.*;


public class ClientImp extends UnicastRemoteObject implements InterfaceClient{

	public List<String> keys;

	public ClientImp() throws RemoteException{
		super();
		keys = new ArrayList<String>();
	}

	public void sendMessage(String message) throws RemoteException{
		System.out.println(message);
	}


	public void saveContentKey(String key) throws RemoteException{
		keys.add(key);
	}

	public void removeContentKey(String key) throws RemoteException{
		keys.add(key);
	}

}