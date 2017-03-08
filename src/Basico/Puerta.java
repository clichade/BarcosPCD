
public class Puerta {


	private static Puerta instance = null;
	TorreDeControl torre;
	Zona_De_Carga zonaDeCarga;

	private Puerta() {
		torre = new TorreDeControl();
		zonaDeCarga = new Zona_De_Carga();
	}

	//síngleton por 2 check que comprueba y luego una vez mas para instanciar son syncronized
	//y asegurarnos de que no metemos mas instancias durante los threads
	public static Puerta getInstance() {
		if (instance == null) {
			synchronized (Puerta.class) {
				if (instance == null) {
					instance = new Puerta();
				}
			}
		}
		return instance;
	}

	/**
	 * los petroleros empiezan con su proceso de zona de carga una vez HAN ACABAO EL FIN ENTRADA
	 * @param b
	 */
	public void enter(Barco b) {
		torre.permEntrada(b);
		System.out.println(b.id + " ha ENTRADO");
		System.out.println(b.id + " ha ENTRADO");
		System.out.println(b.id + " ha ENTRADO");
		System.out.println(b.id + " FIN_ENTRADA");
		torre.finEntrada();
		if (b.isPetrolero())
			zonaDeCarga.encallar(b);
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
