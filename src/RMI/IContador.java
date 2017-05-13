package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IContador extends Remote {
	
	 void incBEntrar() throws RemoteException;
	
	 void incBSalir() throws RemoteException;
	 
	 void decBEntrar() throws RemoteException;
	 
	 void decBSalir() throws RemoteException;

}
