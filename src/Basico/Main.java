package Basico;

import Basico.Barco;

import java.util.List;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		List<Thread> ListaBarcos = new ArrayList<>();
		boolean entrada = true;

		for (int i = 0; i < 20; i++) {
			int ident = i + 1;
			entrada = !entrada;
			Barco ship = new Barco(ident, entrada);
			ListaBarcos.add(new Thread(ship));

		}

		for (int i = 20; i < 25; i++) {
			int ident = i + 1;
			Barco ship = new Petrolero(ident, true);
			ListaBarcos.add(new Thread(ship));

		}

		Barco ship = new Mercante(25,true);
		ListaBarcos.add(new Thread(ship));


		for (int i = 0; i < ListaBarcos.size(); i++) {
			ListaBarcos.get(i).start();
		}

		try {
			for (int i = 0; i < ListaBarcos.size(); i++)
				ListaBarcos.get(i).join();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
