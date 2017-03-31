package Basico;

import Basico.Barco;

import java.util.LinkedList;

public class TorreDeControl {

	private int entrando;
	private int saliendo;


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
	}


	/**
	 * método que hace que un barco pida permiso de entrada, un barco solo puede entrar si se cumple que:
	 * NO HAY NINGÚN BARCO SALIENDO
	 * NO HAY NINGÚN BARCO ESPERANDO PARA SALIR
	 * EL ES EL PRIMERO EN LA LISTA DE ESPERA DE LOS BARCOS DE ENTRADA
	 * en ese caso entra y se elimina de la lista de espera
	 * @param b
	 */
	public synchronized void permEntrada(Barco b) {
		System.out.println(b.id + " Solicita entrar");
		colaEntrada.add(b);
		try {
			while (saliendo > 0 || !colaSalida.isEmpty() || !colaEntrada.getFirst().equals(b)) {
				wait();
			}
		} catch (InterruptedException e) {

		}
		entrando++;
		colaEntrada.removeFirst();
	}

	/**
	 * método que hace que un barco pida permiso de salida, un barco solo puede entrar si se cumple que:
	 * NO HAY NINGÚN BARCO ENTRANDO
	 * EL ES EL PRIMERO EN LA LISTA DE ESPERA DE LOS BARCOS DE SALIDA
	 * en ese caso entra y se elimina de la lista de espera
	 * con esto dejamos claro que los barcos de salida tienen prioridad ya que no necesitan
	 * saber si hay un barco esperando a salir
	 * @param b
	 */
	public synchronized void permSalida(Barco b) {
		System.out.println(b.id + " Solicita salir");
		colaSalida.add(b);
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
	 * si ha acabado de entrar se notifica y si no hay nadie entrando se les notifica a los de salida
	 */
	public synchronized void finEntrada() {
		entrando--;
		if (entrando == 0 ) {
			notifyAll();
		}
		System.out.println("eoooo");

	}

	public synchronized void finSalida() {
		saliendo--;
		if (saliendo == 0) {
			notifyAll();
		}
		System.out.println("eoooo");
	}
}
