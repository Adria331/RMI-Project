
public class ServerImp extends UnicastRemoteObject implements ServerInterface{
	private String filePath;
	private String ipAdd;
	private int port;


	public ServerImp() throws RemoteException{
		super();
	}


	public void registerClient(User user) throws RemoteException{

	}


	public void discardClient(User user) throws RemoteException{

	}


	public List<String> getContent(String description) throws RemoteException{

	}


	public void downloadContent(String title) throws RemoteException{

	} 


	public void notifyAllClients() throws RemoteException{

	}


	public getContentAdvanced() throws RemoteException{

	}


	public void downloadAdvanced() throws RemoteException{

	} 


	public void modifyContentTitle(String oldTitle, String newTitle,  User user) throws RemoteException{

	}


	public void deleteContent(String title, User user) throws RemoteException{

	}
}