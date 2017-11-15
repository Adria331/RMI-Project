import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server{

	private static int port;
	private static String ipAdd; // rmi://localhost:" + PORT + ...

	public static void main(String args[]){

	}


	public static void startRegistry(){
		try{
			Registry registry= LocateRegistry.getRegistry(this.port);
        	registry.list( );
        }catch(RemoteException ex){
        	System.out.println("RMI registry cannot be located at port" + this.port);
        	LocateRegistry.createRegistry(PORT);
        	System.out.println("RMI registry created at port " + this.port);
        }
	}

	

}