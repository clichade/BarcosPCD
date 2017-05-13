package Basico;

import DescargaContenedores.Plataforma;
import SistemaDeCarga.Zona_De_Carga;

import java.rmi.RemoteException;
import java.util.concurrent.BrokenBarrierException;

public class Puerta {


	private static Puerta instance = null;
	TorreDeControl torre;
	Zona_De_Carga zonaDeCarga;
	Plataforma plataforma;

	private Puerta() {
		torre = new TorreDeControl();
		zonaDeCarga = new Zona_De_Carga();
		plataforma = new Plataforma();

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
	public void enter(Barco b)  {
		try {
			torre.permEntrada(b);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(b.id + " ha ENTRADO");
		System.out.println(b.id + " ha ENTRADO");
		System.out.println(b.id + " ha ENTRADO");
		System.out.println(b.id + " FIN_ENTRADA");
		torre.finEntrada();
		if (Petrolero.class.isInstance(b)) {
				zonaDeCarga.encallar();
		}else if (Mercante.class.isInstance(b)){
			plataforma.encallar();
		}
	}

	public void exit(Barco b) {
		try {
			torre.permSalida(b);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(b.id + " ha SALIDO");
		System.out.println(b.id + " ha SALIDO");
		System.out.println(b.id + " ha SALIDO");
		System.out.println(b.id + " FIN_SALIDA");
		torre.finSalida();
	}
}
