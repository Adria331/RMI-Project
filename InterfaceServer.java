

import java.rmi.*;
import java.rmi.RemoteException;
import java.util.List;


public interface InterfaceServer extends Remote{

	//Lvl parguel
	public void registerClient(String username,  InterfaceClient client) throws RemoteException; //Afegir user a la llista de clients -> server pot notificar a tots a l'hora
	public void discardClient(InterfaceClient client) throws RemoteException; //Treure client de la llista

	public String getFileName(String title, InterfaceClient client) throws RemoteException;

	public void uploadContent(String title, String desc, String filename, String path, byte[] data, InterfaceClient client) throws RemoteException;
	public List<String> getContent(String description, InterfaceClient client) throws RemoteException; // Llista fitxers a traves de "textual description"
	public byte[] downloadContent(String title, InterfaceClient client) throws RemoteException; // Se descargue fitxer tenin el seu titol

	//Lvl pro
	public void notifyAllClients(InterfaceClient client) throws RemoteException; //New content = avisar a tots



	public List<String> getContentAdvanced(InterfaceClient client) throws RemoteException; /* The Client can access to distributed contents using a textual description. The Client
sends the request to an RMI Server. The RMI Server receive the textual
description, perform a global search and returns the list of ALL contents (titles) in
the distributed system related to this description */

	public void downloadAdvanced(InterfaceClient client) throws RemoteException; /* The Client can download a distributed digital content. When a Client request for a
digital content is not locally stored the, The RMI Server redirect the petition to the
source server in a transparent way for the client. */


	// Opcional 
	public void modifyContentTitle(String oldTitle, String newTitle, InterfaceClient client) throws RemoteException;
	public void deleteContent(String title, InterfaceClient client) throws RemoteException;

	public void listAllMyContent(InterfaceClient client) throws RemoteException;
	
        public void addServer(InterfaceServer server) throws RemoteException;
        public void setServers(List<InterfaceServer> list) throws RemoteException;
        
        public List<Content> returnContents() throws RemoteException;
}
