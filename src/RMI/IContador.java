package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IContador extends Remote {

	void incBEntrar() throws RemoteException;

	void incBSalir() throws RemoteException;

	void incSal() throws RemoteException;

	void incHarina() throws RemoteException;

	void incAzucar() throws RemoteException;

	int getEntr() throws RemoteException;

	int getSali() throws RemoteException;

	int getAzu() throws RemoteException;

	int getHari() throws RemoteException;

	int getSal() throws RemoteException;
	


}
