
public class Barco implements Runnable {
	int id;
	private boolean entra;
	private boolean petrolero;

	public Barco(int ident, boolean entrada, boolean es_petrolero) {
		id = ident;
		entra = entrada;
		petrolero = es_petrolero;
	}

	public boolean isPetrolero() {
		return petrolero;
	}

	public void run() {
		if (!entra) {
			Puerta.getInstance().exit(this);
		} else
			Puerta.getInstance().enter(this);
	}
}
