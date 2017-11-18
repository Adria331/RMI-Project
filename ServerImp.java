
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.List;

public class ServerImp extends UnicastRemoteObject implements InterfaceServer{

	private static String filePath;
	private static int port = 4000;
	private static String ipAdd = "localhost";


	public ServerImp() throws RemoteException{
		super();
	}


	public void registerClient(User user) throws RemoteException{

	}


	public void discardClient(User user) throws RemoteException{

	}


	public List<String> getContent(String description) throws RemoteException{
		return null;
	}


	public void downloadContent(String title) throws RemoteException{

	} 


	public void notifyAllClients() throws RemoteException{

	}


	public List<String> getContentAdvanced() throws RemoteException{
		return null;
	}


	public void downloadAdvanced() throws RemoteException{

	} 


	public void modifyContentTitle(String oldTitle, String newTitle,  User user) throws RemoteException{

	}


	public void deleteContent(String title, User user) throws RemoteException{

	}

	public void setAdd(String add){
		this.ipAdd = add;
	}

	public void setPort(int port){
		this.port = port;
	}
}