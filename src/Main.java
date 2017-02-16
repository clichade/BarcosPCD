import java.util.ArrayList;

/**
 * Created by trjano on 14/02/17.
 */
public class Main {
    public static void main(String[] args) {


        ArrayList<Barco> listaBarcos = new ArrayList<>();

        boolean entrar = true;

        for (int i = 0; i < 50; i++) {
            if(i % 2 == 0) entrar = true;
            else entrar = false;

            listaBarcos.add(new Barco(i, entrar));
        }


        for (int i=0;i<listaBarcos.size();i++){
            Thread t = new Thread(listaBarcos.get(i));
            t.start();
        }
    }
}
