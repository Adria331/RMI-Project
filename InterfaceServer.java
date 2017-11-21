

import java.rmi.*;
import java.rmi.RemoteException;
import java.util.List;


public interface InterfaceServer extends Remote{


	public void registerClient(String username,  InterfaceClient client) throws RemoteException;
	public void discardClient(InterfaceClient client) throws RemoteException; 

	public String getFileName(String title, InterfaceClient client) throws RemoteException;

	public void uploadContent(String title, String desc, String filename, String path, byte[] data, InterfaceClient client) throws RemoteException;
	public List<String> getContent(String description, InterfaceClient client) throws RemoteException; 
	public byte[] downloadContent(String title, InterfaceClient client) throws RemoteException; 


	public void notifyAllClients(InterfaceClient client) throws RemoteException; 

	public void modifyContentTitle(String oldTitle, String newTitle, InterfaceClient client) throws RemoteException;
	public void deleteContent(String title, InterfaceClient client) throws RemoteException;

	public void listAllMyContent(InterfaceClient client) throws RemoteException;
	
	public void addServer(InterfaceServer server) throws RemoteException;
	public void setServers(List<InterfaceServer> list) throws RemoteException;
        
    public List<Content> returnContents() throws RemoteException;
    public byte[] getBytesFile(String title) throws RemoteException;

    public void encodeXML(Content c) throws RemoteException;
    public void decodeXML() throws RemoteException;

    }
