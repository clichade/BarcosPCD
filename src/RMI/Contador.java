package RMI;

import java.rmi.RemoteException;

public class Contador implements IContador {
	
	
	private  int barcosE = 0;
	
	private  int barcosS = 0;
	
	private static Ventana v = new Ventana("Control de Barcos");
	
	public Contador() {
		v.addText("Control de tráfico de barcos");
	}

	@Override
	public void incBEntrar() {
		barcosE++;
		v.addText("\n Barcos esperando a entrar "+ barcosE);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void incBSalir() {
		barcosS++;
		v.addText("\n Barcos esperando a salir "+ barcosS);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decBEntrar() throws RemoteException {
		barcosE--;
		
	}

	@Override
	public void decBSalir() throws RemoteException {
		barcosS--;
		
	}

}
