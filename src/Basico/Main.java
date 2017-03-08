import java.util.List;
import java.util.ArrayList;

public class MainBarco {

	public static void main(String[] args) {
		List<Thread> ListaBarcos = new ArrayList<>();
		boolean entrada = true;

		for (int i = 0; i < 20; i++) {
			int ident = i + 1;
			entrada = !entrada;
			Barco ship = new Barco(ident, entrada,false);
			ListaBarcos.add(new Thread(ship));

		}

		for (int i = 0; i < ListaBarcos.size(); i++) {
			ListaBarcos.get(i).start();
		}

		try {
			for (int i = 0; i < ListaBarcos.size(); i++)
				ListaBarcos.get(i).join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Los pares han salido y los impares han entrado.");
	}
}
