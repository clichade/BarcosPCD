import java.util.ArrayList;

/**
 * Created by trjano on 14/02/17.
 */
public class Main {
    public static void main(String[] args) {


        ArrayList<Barco> listaBarcos = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            listaBarcos.add(new Barco(i, true));
        }

        for (int i = 30; i < 60; i++) {
            listaBarcos.add(new Barco(i, false));
        }

        for (int i=0;i<listaBarcos.size();i++){
            Thread t = new Thread(listaBarcos.get(i));
            t.start();
        }
    }
}
