package RMI;

import java.rmi.RemoteException;

public class Contador implements IContador {

	int barcosE;
	int barcosS;
	int azucar;
	int harina;
	int sal;
	private static Contador x;

	public Contador() {
		barcosE = 0;
		barcosS = 0;
		azucar = 0;
		harina = 0;
		sal = 0;
	}

	public static void generateInstance() {
		x = new Contador();
	}

	@Override
	public void incBEntrar() {
		barcosE++;
		// TODO Auto-generated method stub

	}

	@Override
	public void incBSalir() {
		barcosS++;

	}

	@Override
	public void incSal() throws RemoteException {
		// TODO Auto-generated method stub
		sal++;
	}

	@Override
	public void incHarina() throws RemoteException {
		// TODO Auto-generated method stub
		harina++;
	}

	@Override
	public void incAzucar() throws RemoteException {
		// TODO Auto-generated method stub
		azucar++;
	}

	@Override
	public int getEntr() throws RemoteException {
		// TODO Auto-generated method stub
		return barcosE;
	}

	@Override
	public int getSali() throws RemoteException {
		// TODO Auto-generated method stub
		return barcosS;
	}

	@Override
	public int getAzu() throws RemoteException {
		// TODO Auto-generated method stub
		return azucar;
	}

	@Override
	public int getHari() throws RemoteException {
		// TODO Auto-generated method stub
		return harina;
	}

	@Override
	public int getSal() throws RemoteException {
		// TODO Auto-generated method stub
		return sal;
	}

	public static Contador getInstance() {
		return x;
	}

}
