
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.rmi.*;

import java.net.MalformedURLException;

import java.util.List;
import java.util.Scanner;

public class Client{

	public static String username = null;
	public static String password;

	public static String filePath = "/home/adria/rmi/content/";

	public static ClientImp client;
	public static InterfaceServer server;

	public static void main(String args[]){
		
		int port = 4000;
		String ip = "localhost";
		String url = "rmi://" + ip + ":" + Integer.toString(port) + "/mytube";

		try{
			server = (InterfaceServer) Naming.lookup(url);
		}catch(NotBoundException ex){
			System.out.println("The url is not currently bound");
			System.exit(0);
		}catch(MalformedURLException ex){
			System.out.println("Registry has not an appropiate url");
			System.exit(0);
		}catch(RemoteException ex){
			System.out.println("Registry cannot be contacted");
			System.exit(0);
		}

		// All the options
		try{
			client = new ClientImp();
			choice();

		}catch(RemoteException ex){
			System.out.println("A RemoteException has been caught");
		}

	}

	public static String scanner(String message){
		System.out.println(message);
		Scanner scan = new Scanner(System.in);
		String text = scan.nextLine();
		return text;
	}

	public static void choice() throws RemoteException{
		while(true){
			if(username == null){
				while(true){
					String opcions = "\n///////////////////////////////////// \n"+
					"0 = register \n" +
					"1 = login \n"+
					"///////////////////////////////////// \n";

					String escollit = scanner(opcions);
					
					if(Integer.parseInt(escollit) == 0){
						System.out.println("You have choosed to register");
						register();
					}
					else if(Integer.parseInt(escollit) == 1){
						System.out.println("You have choosed to log in");
						login();
					}else{
						System.out.println("Not a valid Option");
					}

					System.out.println("/////////////////////////////////////");
					if(username != null)
						break;
				}

			}else{
				while(true){
					String opcions = "\n///////////////////////////////////// \n"+
						"0 = Disconnect \n" +
						"1 = Logout \n"+
						"2 = Upload Content \n"+
						"3 = Get Content by description  \n"+
						"4 = Download Content by Title \n"+
						"5 = Modify Title of your content  \n"+
						"6 = Delete your content \n"+
						"7 = Delete your account \n"+
						"////////////////////////////////// \n";

					String escollit = scanner(opcions);

					if(Integer.parseInt(escollit) == 0){
							System.out.println("You are going to be desconnected from the client");
							System.exit(0);
							
					}else if(Integer.parseInt(escollit) == 1){
							System.out.println("You have choosed to log out");
							username = null;
							password = null;
							break;

					}else if(Integer.parseInt(escollit) == 2){
							upload();
							
					}else if(Integer.parseInt(escollit) == 3){
							System.out.println("You have choosed to Get some Content with your description");
							
					}else if(Integer.parseInt(escollit) == 4){
							System.out.println("You have choosed to download Content with your title");
							
					}else if(Integer.parseInt(escollit) == 5){
							System.out.println("You have choosed to modify a title of your content");
							
					}else if(Integer.parseInt(escollit) == 6){
							System.out.println("You have choosed to delete a content of yours");
							
					}else if(Integer.parseInt(escollit) == 7){
							server.discardClient(username, password, client);
							username = null;
							password = null;
					}else{
						System.out.println("Not a valid Option");
					}

					if(username == null)
						break;

				}//While true
			}//Else si estas loged
		}
	}

	public static void register() throws RemoteException{
		String name = scanner("Username?");
		String pasw = scanner("Password?");
		server.registerClient(name, pasw, client);
	}
	public static void login() throws RemoteException{
		String name = scanner("\nUsername?");
		String pasw = scanner("\nPassword?");
		String[] dades = server.loginClient(name, pasw, client);
		if(dades != null){
			username = dades[0];
			password = dades[1];
		}
	}

	public static void upload() throws RemoteException{
		String title = scanner("Title of the content?");
		String desc = scanner("Description of the content?");
		String filename = scanner("File name? (extension included)");
		System.out.println("The route is "+ filePath + filename);

		try{
			byte[] data = Files.readAllBytes(new File(filePath + filename).toPath());
			server.uploadContent(title, desc, filename, filePath + filename, username, data, client);

		}catch(IOException ex){
			System.out.println("Invalid file");
			System.out.println(ex);
		}
	}
}