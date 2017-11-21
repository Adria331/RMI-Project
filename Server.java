
import java.rmi.*;
import java.net.MalformedURLException;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Server{

	private static int port;
	private static String ip;

	public static void main(String args[]) throws RemoteException, MalformedURLException, AccessException{

		try{
			ServerImp obj = new ServerImp();
			ip = scanner("Select the Ip address of the server (default is localhost)");
			String port2 = scanner("Select the port of the server (default is 4000)");

			if(port2 == null || port2.equals(""))
				port = 4000;
			else{
				port = Integer.parseInt(port2);
			}

			if(ip == null || ip.equals(""))
				ip = "localhost";

			obj.setAdd(ip);
			obj.setPort(port);
			startRegistry();
			String url = "rmi://" + ip + ":" + Integer.toString(port) + "/mytube";
			Naming.rebind(url, obj);
			System.out.println("Server ready");

		}catch(RemoteException ex){
			System.out.println("Server not ready");
		}catch(MalformedURLException ex){
			System.out.println("Registry has not an appropiate url");
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
        	System.out.println("RMI registry cannot be located at port " + port);
        	LocateRegistry.createRegistry(port);
        	System.out.println("RMI registry created at port " + port);
        }
	}

	public static String scanner(String message){
		System.out.println(message);
		Scanner scan = new Scanner(System.in);
		String text = scan.nextLine();
		return text;
	}

	

}
