

import java.rmi.*;

import java.rmi.RemoteException;

import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.ArrayList;

public interface InterfaceServer extends Remote{

	//Lvl parguel
	public void registerClient(User user) throws RemoteException; //Afegir user a la llista de clients -> server pot notificar a tots a l'hora
	public void discardClient(User user) throws RemoteException; //Treure client de la llista
	public List<String> getContent(String description) throws RemoteException; // Llista fitxers a traves de "textual description"
	public void downloadContent(String title) throws RemoteException; // Se descargue fitxer tenin el seu titol

	//Lvl pro
	public void notifyAllClients() throws RemoteException; //New content = avisar a tots

	public List<String> getContentAdvanced() throws RemoteException; /* The Client can access to distributed contents using a textual description. The Client
sends the request to an RMI Server. The RMI Server receive the textual
description, perform a global search and returns the list of ALL contents (titles) in
the distributed system related to this description */

	public void downloadAdvanced() throws RemoteException; /* The Client can download a distributed digital content. When a Client request for a
digital content is not locally stored the, The RMI Server redirect the petition to the
source server in a transparent way for the client. */


	// Opcional 
	public void modifyContentTitle(String oldTitle, String newTitle,  User user) throws RemoteException;
	public void deleteContent(String title, User user) throws RemoteException;
}