
public class Puerta {
	TorreDeControl torre;

	public Puerta() {
		torre = new TorreDeControl();
	}

	public void enter(Barco b) {
		torre.permEntrada(b);
		System.out.println(b.id + " ha ENTRADO");
		System.out.println(b.id + " ha ENTRADO");
		System.out.println(b.id + " ha ENTRADO");
		System.out.println(b.id + " FIN_ENTRADA");
		torre.finEntrada();
	}

	public void exit(Barco b) {
		torre.permSalida(b);
		System.out.println(b.id + " ha SALIDO");
		System.out.println(b.id + " ha SALIDO");
		System.out.println(b.id + " ha SALIDO");
		System.out.println(b.id + " FIN_SALIDA");
		torre.finSalida();
	}
}
