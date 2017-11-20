
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.rmi.*;

import java.net.MalformedURLException;

import java.util.List;
import java.util.Scanner;

public class Client{

	public static String filePath = "/home/adria/rmi/content/";
	public static boolean registered = false;
	public static ClientImp client;
	public static InterfaceServer server;

	public static void main(String args[]){

		String ip = scanner("Select the Ip address of the server (default is localhost)");
		String port = scanner("Select the port of the server (default is 4000)");
		
		if(port == null || port.equals(""))
			port = "4000";

		if(ip == null || ip.equals(""))
			ip = "localhost";

		String url = "rmi://" + ip + ":" + port + "/mytube";

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
			if(registered == false){
				while(true){
					String opcions = "\n///////////////////////////////////// \n"+
					"1 = Register \n" +
					"2 = Exit client \n" +
					"3 = If already registered\n" +
					"///////////////////////////////////// \n";

					String escollit = scanner(opcions);
					
					if(escollit.equals("1")){
						System.out.println("You have chosed to register");
						register();
					}else if(escollit.equals("2")){
						System.out.println("You are going to be desconnected from the client");
						System.exit(0);
					}else if(escollit.equals("3")){
						registered = true;
					}else{
						System.out.println("Not a valid Option");
					}

					System.out.println("/////////////////////////////////////");
					if(registered){
						System.out.println("Those are your options:\n");
						break;
					}
				}

			}else{
				while(true){
					String opcions = "\n///////////////////////////////////// \n"+
						"1 = Exit client \n" +
						"2 = Upload Content \n"+
						"3 = Get Content by description  \n"+
						"4 = Download Content by Title \n"+
						"5 = Modify Title of your content  \n"+
						"6 = Delete content of yours \n"+
						"7 = Remove your client from the server\n"+
						"8 = List your content \n"+
						"9 = Go back \n"+
						"////////////////////////////////// \n";

					String escollit = scanner(opcions);

					if(escollit.equals("1")){
							System.out.println("You are going to be desconnected from the client");
							System.exit(0);



					}else if(escollit.equals("2")){
							upload();
							


					}else if(escollit.equals("3")){
							System.out.println("You have chosed to list content with your description");
							String desc = scanner("Write a description to search content");
							server.getContent(desc, client);


					}else if(escollit.equals("4")){
							System.out.println("You have chosed to download content with your title");
							downloadContentFile();


					}else if(escollit.equals("5")){
							System.out.println("You have chosed to modify a title of your content");
							String oldtitle = scanner("Title of the content you want to change?");
							String newtitle = scanner("New title for the content?");
							server.modifyContentTitle(oldtitle, newtitle, client);


					}else if(escollit.equals("6")){
							System.out.println("You have chosed to delete a content of yours");
							String title = scanner("Title of the content you want to delete?");
							server.deleteContent(title, client);



					}else if(escollit.equals("7")){
							server.discardClient(client);
							System.out.println("Your client was removed from the server");
							registered = false;

					}else if(escollit.equals("8")){
							System.out.println("You have chosed to list your content uploaded");
							server.listAllMyContent(client);

					}else if(escollit.equals("9")){
							registered = false;
									


					}else{
						System.out.println("Not a valid Option");
					}

					if(!registered){
						break;
					}

				}//While true
			}//Else si estas loged
		}
	}

	public static void register() throws RemoteException{
		String name = scanner("Username?");
		server.registerClient(name,  client);
	}

	public static void upload() throws RemoteException{
		String title = scanner("Title of the content?");
		String desc = scanner("Description of the content?");
		String filename = scanner("File name? (extension included)");

		try{
			byte[] data = Files.readAllBytes(new File(filePath + filename).toPath());
			server.uploadContent(title, desc, filename, filePath + filename, data, client);

		}catch(IOException ex){
			System.out.println("Invalid file");
			System.out.println(ex);
		}
	}

	public static void downloadContentFile(){
		String title = scanner("Title of the content you want to download?");
		
		try{
			byte[] data = server.downloadContent(title, client);
			new File(filePath+title).mkdir();
			String filename = server.getFileName(title, client);
			FileOutputStream fos = new FileOutputStream(filePath+title+"/"+ filename);
			fos.write(data);
			fos.close();
			System.out.println("File downloaded at: " + filePath+title+"/"+ filename);
		}catch(IOException ex){
			System.out.println("File couldn't be uploaded");
		}
	}
}