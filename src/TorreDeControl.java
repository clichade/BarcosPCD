import java.util.LinkedList;
import java.util.Queue;

public class TorreDeControl {

	private int entrando;
	private int saliendo;

	Queue<Barco> ColaEntrada = new LinkedList<>();
	Queue<Barco> ColaSalida = new LinkedList<>();

	public TorreDeControl() {
		entrando = 0;
		saliendo = 0;
	}

	public synchronized void permEntrada(Barco b) {
		ColaEntrada.add(b);
		try {
			while (saliendo > 0 || !ColaSalida.isEmpty()) {
				wait();
			}
		} catch (InterruptedException e) {

		}
		entrando++;
		ColaEntrada.remove();
	}

	public synchronized void permSalida(Barco b) {
		ColaSalida.add(b);
		try {
			while (entrando > 0) {
				wait();
			}
		} catch (InterruptedException e) {
		}
		saliendo++;
		ColaSalida.remove();
	}

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
