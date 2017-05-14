package DescargaContenedores;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import RMI.Contador;
import RMI.IContador;

/**
 * Created by trjano on 15/03/17.
 */
public class Plataforma {

	/**
	 * Enumerador que contiene los tipos de contenedores y a la vez el estado de
	 * la base
	 */
	public enum Contenedor {
		VACIO, SAL, HARINA, AZUCAR
	}

	/**
	 * Inicio de la definicion de condiciones y de lock
	 */

	SynchronousQueue<Contenedor> qSal = new SynchronousQueue();
	SynchronousQueue<Contenedor> qAzucar = new SynchronousQueue<>();
	SynchronousQueue<Contenedor> qHarina = new SynchronousQueue<>();
	/**
	 * Fin de la definición de condiciones y el lock
	 */

	Grua gruaSal;
	Grua gruaHarina;
	Grua gruaAzucar;
	GruaBarco gruaBarco;

	// La base de la plataforma que informará de que contiene un contenedor de x
	// tipo o nada(VACIO)
	Contenedor base;

	/**
	 * constructor por defecto inicaliza la base a vacio e instancia las gruas
	 */
	public Plataforma() {
		base = Contenedor.VACIO;
		gruaSal = new Grua(Contenedor.SAL);
		gruaAzucar = new Grua(Contenedor.AZUCAR);
		gruaHarina = new Grua(Contenedor.HARINA);
	}

	/**
	 * en el momento en el que llegu eel barco mercante se instancia su grua y
	 * se empieza a procesar
	 */
	public void encallar() {
		gruaBarco = new GruaBarco();
		procesar();

	}

	/**
	 * startea todos los trheads
	 */
	private void procesar() {
		gruaBarco.start();
		gruaSal.start();
		gruaHarina.start();
		gruaAzucar.start();

	}

	/**
	 * el thread perteneciente al barco el cual contiene la lista de
	 * contenedores
	 */
	private class GruaBarco extends Thread {

		LinkedList<Contenedor> listaCont;
		Random random = new Random();

		/**
		 * inicializa la lsita de contenedores
		 */
		public GruaBarco() {
			listaCont = new LinkedList<>();

			for (int i = 0; i < 12; i++)
				listaCont.add(Contenedor.AZUCAR);
			for (int i = 0; i < 20; i++)
				listaCont.add(Contenedor.SAL);
			for (int i = 0; i < 5; i++)
				listaCont.add(Contenedor.HARINA);
		}

		public void descargar() {

			base = listaCont.remove(random.nextInt(listaCont.size()));// eliminamos
																		// el
																		// primero
																		// y lo
																		// pasamos
																		// a la
																		// base
			// dependiendo del contenido de la base ahora llama a una grua o a
			// otra
			// System.out.println("El barco ha soltado " + base);

			switch (base) {
			case AZUCAR:
				try {

					System.out.println("El barco ha avisado a grua " + base);
					qAzucar.put(base);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				break;
			case HARINA:
				System.out.println("El barco ha avisado a grua " + base);

				try {
					qHarina.put(base);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				break;
			case SAL:
				System.out.println("El barco ha avisado a grua " + base);
				try {
					qSal.put(base);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;

			default:
				System.out.println("Error de algun tipo en el barco");
			}

		}

		@Override
		public void run() {
			while (!listaCont.isEmpty())
				descargar();
		}
	}

	/**
	 * thread grua perteneciente a la estación, dependiendo del tipo de
	 * contenedor la grua reaccionará ante una condicion u otra
	 */
	
	private class Grua extends Thread {
		Contenedor tipo;
		SynchronousQueue queue;
		int n;

		public Grua(Contenedor cont) {
			n = 0;
			switch (cont) {
			case SAL:
				tipo = Contenedor.SAL;
				queue = qSal;
				n = 20;
				break;
			case HARINA:
				tipo = Contenedor.HARINA;
				queue = qHarina;
				n = 5;
				break;
			case AZUCAR:
				tipo = Contenedor.AZUCAR;
				queue = qAzucar;
				n = 12;
			}
		}

		private void coger() throws RemoteException {

			System.out.println("La grua " + tipo + " PRETENDE");
			try {
				queue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			n--;
			IContador x = Contador.getInstance();
			if (tipo == Contenedor.AZUCAR) {
				x.incAzucar();
			} else if (tipo == Contenedor.HARINA) {
				x.incHarina();

			} else if (tipo == Contenedor.SAL) {
				x.incSal();

			}

			System.out.println("La grua " + tipo + " COGIÓ");
		}

		@Override
		public void run() {
			while (n > 0)
				try {
					coger();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

}
