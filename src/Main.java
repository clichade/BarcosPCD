import java.util.ArrayList;

/**
 * Created by trjano on 14/02/17.
 */
public class Main {
    public static void main(String[] args) {


        ArrayList<Thread> listaBarcos = new ArrayList<>();

        boolean entrar = true;

        for (int i = 0; i < 20; i++) {
            if(i % 2 == 0) entrar = true;
            else entrar = false;

            listaBarcos.add(new Thread(new Barco(i, entrar)));
        }


        for (int i=0;i<listaBarcos.size();i++){
            listaBarcos.get(i).start();
        }
    }
}
