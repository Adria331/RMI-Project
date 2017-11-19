
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.*;

public class ServerImp extends UnicastRemoteObject implements InterfaceServer{

	private static String filePath;
	private static int port = 4000;
	private static String ipAdd = "localhost";

	private static Map<String,String> users;

	public ServerImp() throws RemoteException{
		super();
		users = new HashMap<String, String>();
	}


	public void registerClient(String username, String password, InterfaceClient client) throws RemoteException{
		if(users.containsKey(username) == false){
			users.put((String) username, (String) password);
			client.sendMessage("User registered correctly");
			System.out.println("Username: " + username + " Password: " + password);
		}else{
			client.sendMessage("Username already used");
		}
	}

	public String[] loginClient(String username, String pw, InterfaceClient client) throws RemoteException{
		if(users.containsKey(username)){
			if(users.get(username).equals(pw)){
				String[] dades = new String[2];
				dades[0] = username;
				dades[1] = pw;
				client.sendMessage("Login succesful");
				return dades;
			}
		}

		client.sendMessage("Username or password incorrect");
		return null;
	}

	public void discardClient(String username, String password, InterfaceClient client) throws RemoteException{
		
	}


	public List<String> getContent(String description, InterfaceClient client) throws RemoteException{
		return null;
	}


	public void downloadContent(String title, InterfaceClient client) throws RemoteException{

	} 


	public void notifyAllClients(InterfaceClient client) throws RemoteException{

	}


	public List<String> getContentAdvanced(InterfaceClient client) throws RemoteException{
		return null;
	}


	public void downloadAdvanced(InterfaceClient client) throws RemoteException{

	} 


	public void modifyContentTitle(String oldTitle, String newTitle,  String username, String password, InterfaceClient client) throws RemoteException{

	}


	public void deleteContent(String title, String username, String password, InterfaceClient client) throws RemoteException{

	}

	public void setAdd(String add){
		this.ipAdd = add;
	}

	public void setPort(int port){
		this.port = port;
	}
}