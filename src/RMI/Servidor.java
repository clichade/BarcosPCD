package RMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;




public class Servidor {

	public static void main(String[] args) {
		Contador contador = new Contador();
		String identificador = "contador";
		
		try {
			IContador stub=(IContador) UnicastRemoteObject.exportObject(contador, 0);
			
			Registry registry=LocateRegistry.getRegistry();	
			
			registry.rebind(identificador, stub);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}
