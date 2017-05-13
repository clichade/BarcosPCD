package Basico;

import Basico.Barco;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {


	public static void main(String[] args) {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
		List<Barco> listaBarcos = new ArrayList<>();
		boolean entrada = true;

		for (int i = 0; i < 20; i++) {
			entrada = !entrada;
			Barco ship = new Barco(i, entrada);
			listaBarcos.add(ship);

		}

		for (int i = 20; i < 25; i++) {
			int ident = i + 1;
			Barco ship = new Petrolero(ident, true);
			listaBarcos.add(ship);

		}

		Barco ship = new Mercante(25,true);
		listaBarcos.add(ship);


		for (int i = 0; i < listaBarcos.size(); i++) {
			executor.execute(listaBarcos.get(i));
		}

		executor.shutdown();

	}
}
