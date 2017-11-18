
import java.rmi.Naming;

import java.rmi.RemoteException;
import java.rmi.AccessException;
import java.net.MalformedURLException;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server{

	private static int port = 4000;
	private static String ip = "localhost";

	public static void main(String args[]) throws RemoteException, MalformedURLException, AccessException{

		try{
			ServerImp obj = new ServerImp();
			obj.setAdd(ip);
			obj.setPort(port);
			startRegistry();
			String url = "rmi://" + ip + ":" + Integer.toString(port) + "/mytube";
			Naming.rebind(url, obj);
			System.out.println("Server ready");

		}catch(RemoteException ex){
			System.out.println("Server not ready");
		}catch(MalformedURLException ex){
			System.out.println("Registry URL has not an appropiate name");
		}


		/*catch(AccessException ex){
			System.out.println("Operation not permited");
		}*/


	}


	public static void startRegistry() throws RemoteException{
		try{
			Registry registry= LocateRegistry.getRegistry(port);
        	registry.list();
        }catch(RemoteException ex){
        	System.out.println("RMI registry cannot be located at port" + port);
        	LocateRegistry.createRegistry(port);
        	System.out.println("RMI registry created at port " + port);
        }
	}

	

}