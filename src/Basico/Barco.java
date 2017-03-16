package Basico;

public class Barco implements Runnable {
	/**
	 * el código de identificación del barco
	 */
	int id;

	/**
	 * true si es de entrada, false si es de salida
	 */
	private boolean entra;


	public Barco(int ident, boolean entrada) {
		id = ident;
		entra = entrada;
	}


	public void run() {
		if (!entra) {
			Puerta.getInstance().exit(this);
		} else
			Puerta.getInstance().enter(this);
	}
}
