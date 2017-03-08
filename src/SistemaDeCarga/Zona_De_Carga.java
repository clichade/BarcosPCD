import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * Created by trjano on 2/03/17.
 */
public class Zona_De_Carga {

    static final int numTanques = 5;
    Tanque_gasofa[] listaTanques = new Tanque_gasofa[numTanques];
    Tanque_agua tanqueAgua = new Tanque_agua();
    LinkedList<Barco> listaPetroleros = new LinkedList<>();
    Semaphore reponedor = new Semaphore(1);


    public synchronized void encallar(Barco petrolero) {
        if (listaPetroleros.size() < 5)
            listaPetroleros.add(petrolero);
    }

    private void repostarGasolina(Barco b){
        
        int gasolina = 0;



    }

    /**
     * pone a mil todos los contadores de agua
     */
    public void reponer() {
        for (int i = 0; i < numTanques; i++) {
            listaTanques[i].rellenar();
        }
    }

    public void repartirAgua(Barco barco) throws InterruptedException {
        tanqueAgua.administrarAgua(barco);
    }


}
