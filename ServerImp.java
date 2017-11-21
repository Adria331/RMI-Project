
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.*;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;

public class ServerImp extends UnicastRemoteObject implements InterfaceServer{

	private static String filePath;
	private static String xmlFilePath;

	private static Map<InterfaceClient, String> users;
	private static Map<String, Content> content;

	private static List<InterfaceServer> servers; //Servers which are connected with you

		
	public ServerImp() throws RemoteException{
		super();
		users = new HashMap<InterfaceClient, String>();
		content = new HashMap<String, Content>(); // key / Content
		servers = new ArrayList<InterfaceServer>();
		filePath = new File("").getAbsolutePath() + "/" + "servercontent/";
		xmlFilePath = new File("").getAbsolutePath() + "/" + "xml/";
		decodeXML();
	}


////// USER MANAGEMENT /////

	public void registerClient(String username, InterfaceClient client) throws RemoteException{
		if(users.containsKey(client) == false && users.containsValue(username) == false){
			users.put(client, (String) username);
			client.sendMessage("User registered correctly");
			System.out.println("User '" + username +"' registered");
		}else if(users.containsValue(username) == true){
			client.sendMessage("Username already used");
		
		}else{
			client.sendMessage("You are already registered");
		}
	}

	public void discardClient(InterfaceClient client) throws RemoteException{
		client.sendMessage("You have choosed to discard your client");
		if(users.containsKey(client)){
			String name = users.get(client);
			users.remove(client);
			System.out.println("Client " + name + " deleted");
			client.sendMessage("Deleted succesfully");
		}else{
			client.sendMessage("Couldn't be removed");
		}
	}

////////////////////////////////////


	// K 
	public void uploadContent(String title, String desc, String filename, String path, byte[] data, InterfaceClient client) throws RemoteException{
		if(!users.containsKey(client)){
			client.sendMessage("You are not registered");
		}else{
			try{
				//Generates a random, unique key to associate with the content
				String key = UUID.randomUUID().toString();

				Content c = new Content(title, desc, filename, path);
				//Add its data
				c.setContentKey(key);
				c.setOwnerName(users.get(client));
				c.setFilepath(filePath+key+"/"+filename);

				content.put(title, c);

				//Creates the folder with the key as its name				
				new File(filePath+key).mkdir();

				//Saves the file in the new folder
				FileOutputStream fos = new FileOutputStream(filePath+key+"/"+filename);
				fos.write(data);
				fos.close();

				encodeXML(c);
				client.sendMessage(c.getfilepath() + " uploaded successfuly");
				System.out.println("File uploaded: " + filename);
				notifyAllClients(client);
			}catch(IOException ex){
				client.sendMessage("File couldn't be uploaded");
				System.out.println(ex);
			}
		}
	}

	public List<String> getContent(String description, InterfaceClient client) throws RemoteException{
		List<Content> list = new ArrayList<Content>(content.values());
		List<String> titles = new ArrayList<String>();

		for (Content c : list){
			if(c.getDescription().equals(description))
				titles.add(c.getTitle());
		}
	    
        for(InterfaceServer s : servers){
            List<Content> otherContent = s.returnContents();
            for(Content c : otherContent){
                if(c.getDescription().equals(description))
		        	titles.add(c.getTitle());
        	}
        }
                
		if(titles.size() == 0)
			client.sendMessage("There is no title coincident with that description");
		else{
			client.sendMessage("These are the contents relationed with that description:\n");
			for(String contentTitle : titles){
				client.sendMessage("- " + contentTitle);
			}
		}

		return titles;
	}


	public byte[] downloadContent(String title, InterfaceClient client) throws RemoteException{
		if(content.containsKey(title)){
			try{
				return Files.readAllBytes(new File(content.get(title).getfilepath()).toPath());
			}catch(IOException ex){
				client.sendMessage("This content does not exist");
			}
		}else{
	        for(InterfaceServer s : servers){
            	List<Content> otherContent = s.returnContents();
            	for(Content c : otherContent){
                	if(c.getTitle().equals(title))
		        		return s.getBytesFile(title);
    			}
       		}
		}

		client.sendMessage("There are no content with that title");
		return null;
	} 


	public void notifyAllClients(InterfaceClient client) throws RemoteException{
		List<InterfaceClient> clientList = new ArrayList<InterfaceClient>(users.keySet());
		for(InterfaceClient clientsito : clientList){
			clientsito.sendMessage("New content has been added");
		}
	}


	public void modifyContentTitle(String oldTitle, String newTitle,  InterfaceClient client) throws RemoteException{
		if(content.containsKey(oldTitle)){
			Content c = content.get(oldTitle);
			if(c.getOwnerName() == users.get(client)){
			
				content.remove(oldTitle);
				c.setTitle(newTitle);
				content.put(newTitle, c);
				client.sendMessage("Content title " + oldTitle + " changed to " + newTitle);
			
			}else{
				client.sendMessage("You are not the owner of that content");
			}
		}else
			client.sendMessage("There are no content with that title");
	}



	// K 
	public void deleteContent(String title, InterfaceClient client) throws RemoteException{ 
		if(content.containsKey(title)){
			Content c = content.get(title);
			if(c.getOwnerName() == users.get(client)){
				try{
					File file = new File(c.getfilepath());
					if(file.delete()){ // File erased 
						content.remove(title); //Content erased from map
						client.removeContentKey(c.getContentKey()); // Key saved in the client erased
						File folder = new File(filePath+c.getContentKey());
						if(folder.delete()){
							client.sendMessage("Deletion succesful");
						}else{
							client.sendMessage("Couldn't be deleted");
						}
					}else{
						client.sendMessage("Couldn't be deleted");
					}
				}catch(IOException ex){
					client.sendMessage("Deletion unsuccesful");
				}catch(IllegalArgumentException ex){
					client.sendMessage("This file does not already exist");
				}
			}else{
				client.sendMessage("You are not the owner of that content");
			}
		}else{
			client.sendMessage("There are no content with that title");
		}
	}


	public String getFileName(String title, InterfaceClient client) throws RemoteException{
		return content.get(title).getFilename();
	}

	public void listAllMyContent(InterfaceClient client) throws RemoteException{
		List<Content> list = new ArrayList<Content>(content.values());
		for (Content c : list){
			if(c.getOwnerName().equals(users.get(client)))
				client.sendMessage("- "+c.getTitle());
		}
	}
	
	public void setServers(List<InterfaceServer> list) throws RemoteException{
	        this.servers= list;
    }
        
    public void addServer(InterfaceServer server) throws RemoteException{
        System.out.println("Some server has been connected with you");
        this.servers.add(server);
    }
    
    public List<Content> returnContents() throws RemoteException{
        return new ArrayList<Content>(content.values());
    }

    public byte[] getBytesFile(String title) throws RemoteException{
    	try{
    		return Files.readAllBytes(new File(content.get(title).getfilepath()).toPath());
		}catch(IOException ex){
			return null;
		}
    }

    public void encodeXML(Content c) throws RemoteException{
    	try{
    		System.out.println("Generating "+ xmlFilePath + c.getTitle() + ".xml");
    		XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(xmlFilePath + c.getTitle() + ".xml")));
    		e.writeObject(c);
    		e.close();
    	}catch(IOException ex){
    	}
    }

    public void decodeXML() throws RemoteException{
    	XMLDecoder e;

    	if(!(new File(new File("").getAbsolutePath() + "/" + "xml").exists()))
    		return;

    	try{
	    	for(File f : new File(xmlFilePath).listFiles()){
				e = new XMLDecoder(new BufferedInputStream(new FileInputStream(f)));
				Object result = e.readObject();
				Content c = (Content) result;
				content.put(c.getTitle(), c);
				e.close();
	    	}
    	}catch(IOException ex){
    		System.out.println("Can't load previous files");
    	}
    }
}
