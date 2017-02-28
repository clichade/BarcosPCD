import java.util.LinkedList;
import java.util.Queue;

public class TorreDeControl {

	private int entrando;
	private int saliendo;


	protected LinkedList<Barco> colaEntrada = new LinkedList<>();
	protected LinkedList<Barco> colaSalida = new LinkedList<>();

	public TorreDeControl() {
		entrando = 0;
		saliendo = 0;
	}


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

	public synchronized void finEntrada() {
		entrando--;
		if (entrando == 0 ) {
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
