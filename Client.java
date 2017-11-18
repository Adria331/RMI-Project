
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.rmi.*;

import java.net.MalformedURLException;

import java.util.List;

public class Client{

	public static User user;
	public static String filePath;

	public static ClientImp client;

	public static void main(String args[]){
		
		int port = 4000;
		String ip = "localhost";
		String url = "rmi://" + ip + ":" + Integer.toString(port) + "/mytube";

		try{
			InterfaceServer server = (InterfaceServer) Naming.lookup(url);
		}catch(NotBoundException ex){
			System.out.println("The url is not currently bound");
		}catch(MalformedURLException ex){
			System.out.println("Registry has not an appropiate url");
		}catch(RemoteException ex){
			System.out.println("Registry cannot be contacted");
		}

		// All the options
		try{
			client = new ClientImp();
			options();

		}catch(RemoteException ex){
			System.out.println("A RemoteException has been caught");
		}

	}

	public static void options() throws RemoteException{
	}

}