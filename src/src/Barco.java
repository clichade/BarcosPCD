
public class Barco implements Runnable {
	Puerta door;
	int id;
	boolean entra;

	public Barco(Puerta p, int ident, boolean entrada) {
		door = p;
		id = ident;
		entra = entrada;
	}

	public void run() {
		if (!entra) {
			door.exit(this);
		} else
			door.enter(this);
	}
}
