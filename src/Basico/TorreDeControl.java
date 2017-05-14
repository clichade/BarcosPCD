package Basico;

import Basico.Barco;
import RMI.Contador;
import RMI.IContador;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

public class TorreDeControl {

	private int entrando;
	private int saliendo;

	Contador contador;

	/**
	 * la cola de ESPERA de los barcos de entrada
	 */
	protected LinkedList<Barco> colaEntrada = new LinkedList<>();

	/**
	 * la cola de ESPERA de los barcos de salida
	 */
	protected LinkedList<Barco> colaSalida = new LinkedList<>();

	public TorreDeControl() {
		entrando = 0;
		saliendo = 0;
		Contador.generateInstance();
		contador = Contador.getInstance();

		String identificador = "localhost";

		try {
			IContador stub = (IContador) UnicastRemoteObject.exportObject(
					contador, 0);

			Registry registry = LocateRegistry.getRegistry();

			registry.rebind(identificador, stub);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * método que hace que un barco pida permiso de entrada, un barco solo puede
	 * entrar si se cumple que: NO HAY NINGÚN BARCO SALIENDO NO HAY NINGÚN BARCO
	 * ESPERANDO PARA SALIR EL ES EL PRIMERO EN LA LISTA DE ESPERA DE LOS BARCOS
	 * DE ENTRADA en ese caso entra y se elimina de la lista de espera
	 * 
	 * @param b
	 * @throws RemoteException
	 */
	public synchronized void permEntrada(Barco b) throws RemoteException {
		System.out.println(b.id + " Solicita entrar");
		colaEntrada.add(b);
		contador.incBEntrar();
		try {
			while (saliendo > 0 || !colaSalida.isEmpty()
					|| !colaEntrada.getFirst().equals(b)) {
				wait();
			}
		} catch (InterruptedException e) {

		}

		entrando++;
		colaEntrada.removeFirst();
	}

	/**
	 * método que hace que un barco pida permiso de salida, un barco solo puede
	 * entrar si se cumple que: NO HAY NINGÚN BARCO ENTRANDO EL ES EL PRIMERO EN
	 * LA LISTA DE ESPERA DE LOS BARCOS DE SALIDA en ese caso entra y se elimina
	 * de la lista de espera con esto dejamos claro que los barcos de salida
	 * tienen prioridad ya que no necesitan saber si hay un barco esperando a
	 * salir
	 * 
	 * @param b
	 * @throws RemoteException
	 */
	public synchronized void permSalida(Barco b) throws RemoteException {
		System.out.println(b.id + " Solicita salir");
		colaSalida.add(b);
		contador.incBSalir();
		try {
			while (entrando > 0 || !colaSalida.getFirst().equals(b)) {
				wait();
			}
		} catch (InterruptedException e) {
		}
		saliendo++;
		colaSalida.removeFirst();
	}

	/**
	 * si ha acabado de entrar se notifica y si no hay nadie entrando se les
	 * notifica a los de salida
	 */
	public synchronized void finEntrada() {
		entrando--;
		if (entrando == 0) {
			notifyAll();
		}

	}

	public synchronized void finSalida() {
		saliendo--;
		if (saliendo == 0) {
			notifyAll();
		}

	}
}
