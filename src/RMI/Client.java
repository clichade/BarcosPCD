package RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		Ventana cont = new Ventana("Contenedores");
		Ventana barcos = new Ventana("Barcos");
		if (args.length < 2) {
			System.out.println("Error en argumentos: formato de uso");
			System.out
					.println("java Client <host_servidor> <nombre_objeto_servidor>");
		} else {
			try {
				Registry registry = LocateRegistry.getRegistry(args[0]);
				IContador stub = (IContador) registry.lookup(args[1]);
				barcos.addText("\n*************Barcos entrantes/salientes*************");
				barcos.addText("\nBarcos que han entrado: " + stub.getEntr());
				barcos.addText("\nBarcos que han salido: " + stub.getSali());

				cont.addText("\n*************Contenedores Descargados*************");
				cont.addText("Contenedores de Azucar:" + stub.getAzu());
				cont.addText("Contenedores de Sal:" + stub.getSal());
				cont.addText("Contenedores de Harina:" + stub.getHari());

				// En caso de quererlo por consola y con un option select que te
				// permita elegir
				// Scanner x = new Scanner(System.in);
				// int v;

				// System.out.println("¿Qué quieres ver?:");
				// System.out.println("1 - Barcos que han entrado y salido.");
				// System.out.println("2 - Contenedores de cada tipo.");
				// do {
				// v = x.nextInt();
				// if (v == 1) {
				// System.out.println("Barcos que han entrado: "
				// + stub.getEntr());
				// System.out.println("Barcos que han salido:"
				// + stub.getSali());
				//
				// } else if (v == 2) {
				// System.out.println("Contenedores de Azucar:"
				// + stub.getAzu());
				// System.out.println("Contenedores de Sal:"
				// + stub.getSal());
				// System.out.println("Contenedores de Harina:"
				// + stub.getHari());
				// } else {
				// System.out.println("Introduce una opción correcta");
				// }
				// } while (v != 2 && v != 1);
				// String response = stub.sayHello();
			} catch (Exception e) {
				System.err.println("Client exception: " + e.toString());
				e.printStackTrace();
			}
		}
	}
}
