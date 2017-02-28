
public class Puerta {
	TorreDeControl Torre;

	public Puerta() {
		Torre = new TorreDeControl();
	}

	public void enter(Barco b) {
		Torre.permEntrada(b);
		System.out.println("El barco " + b.id + " ha ENTRADO");
		System.out.println("El barco " + b.id + " ha ENTRADO");
		System.out.println("El barco " + b.id + " ha ENTRADO");
		System.out.println("El barco " + b.id + " finalizará  su entrada");
		Torre.finEntrada();
	}

	public void exit(Barco b) {
		Torre.permSalida(b);
		System.out.println("El barco " + b.id + " ha SALIDO");
		System.out.println("El barco " + b.id + " ha SALIDO");
		System.out.println("El barco " + b.id + " ha SALIDO");
		System.out.println("El barco " + b.id + " finalizará  su salida");
		Torre.finSalida();
	}
}
