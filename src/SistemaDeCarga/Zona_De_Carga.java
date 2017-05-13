package SistemaDeCarga;

import Basico.Barco;

import java.util.LinkedList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * Created by trjano on 2/03/17.
 */
public class Zona_De_Carga {

    static final int numTanques = 5;

    /**
     * la lista de tanques de gasolina
     */
    private Tanque_gasofa[] listaTanques = new Tanque_gasofa[numTanques];

    /**
     * lista de los threads encargados de cargar la gasolina
     */
    private LinkedList<Thread> threadsGasolina;

    /**
     * lista de los threads encragados de cargar el agua
     */
    private LinkedList<Thread> threadsAgua;

    /**
     * semáforo encargado de impedir que el reponedor reponga cuando hay barcos descargando y viceversa
     */
    private Semaphore reponedor = new Semaphore(1);

    /**
     * semáforo encargado de garantizar la exclusión mutua a la hora de alterar los contadores
     * de la thread gaslina.
     */
    private Semaphore mutex = new Semaphore(1);


    /**
     * Al solo haber uno y tener capacidad ilimitada lo representamos como un semáforo asecas
     */
    Semaphore tanqueAgua = new Semaphore(1);


    /**
     * número de barcos que están repostando gasolina en el momento actual,
     * empieza a cero
     */
    private int n_repost_g = 0;


    boolean started = false;


    CyclicBarrier barrier = new CyclicBarrier(5);

    /**
     * constructor por defecto, instanciamos nuestras estructuras de datos
     */
    public Zona_De_Carga() {
        for (int i = 0; i<numTanques;i++){
            listaTanques [i] = new Tanque_gasofa();
        }
        threadsGasolina = new LinkedList<>();
        threadsAgua = new LinkedList<>();

    }

    /**
     * ,etodo que crea los theads de gasolina y de agua a medida que van llegando los barcos, solo puede
     * añadirse uno a la vez y en el momento que son 5 se empieza a procesar
     */
    public  void encallar() {
        synchronized (this) {
            int pos = threadsGasolina.size();
            threadsGasolina.add(new Thread_Gasolina(pos));
            threadsAgua.add(new Thread_Agua(pos));
        }
        try { barrier.await();} catch (InterruptedException e) {e.printStackTrace();} catch (BrokenBarrierException e) {e.printStackTrace();}

        synchronized (this) {
            if(!started) {
                procesar();
                started = true;
            }
        }


    }

    /**
     * lanzamos todos los threads
     */
    public void procesar() {
        for (int i=0; i< threadsGasolina.size();i++) {
            threadsAgua.get(i).start();
            threadsGasolina.get(i).start();
        }
        new Thread_Reponedor().start();
    }


    /**
     *
     * @return true si todos los tanques tienen 0 litros, false en caso contrario
     */
    private boolean tanquesVacios() {
        for (Tanque_gasofa t : listaTanques) {
            if (!t.estaVacio())
                return false;
        }
        return true;
    }

    /**
     * thread encargado de reponer  los tanques de gasolina mientras los barcos esten vivos y los rellena
     * solo si TODOS los tanques están vacios
     */
    private class Thread_Reponedor extends Thread {

        /**
         * para cuando todos los threads de rellenar gasolina esten muertos
         *
         * @return
         */
        private boolean quierenRellenar() {
            for (Thread t : threadsGasolina) {
                if (t.isAlive())
                    return true;
            }
            return false;
        }

        /**
         * Solicita reponer gasolina y si todos los tanques están vacíos los rellena a full todos a la vez
         * antes de dejarlos libres
         *
         * @throws InterruptedException
         */
        private void reponerGasolina() throws InterruptedException {
            reponedor.acquire();
            if (tanquesVacios()) {
                System.out.println("Reponiendo gasolina");
                for (int i = 0; i < numTanques; i++)
                    listaTanques[i].rellenar();
            }
            reponedor.release();

        }

        @Override
        public void run() {
            while (quierenRellenar()) {
                try {
                    reponerGasolina();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    /**
     * thread encargado de vaciar la gasolina de los tanques de 250 en 250 hasta un límite de 3000
     * la variable pos hace referencial tanque que irán del que consumirán
     */
    private class Thread_Gasolina extends Thread {
        int pos;

        Thread_Gasolina(int pos) {
            this.pos = pos;
        }
        private void cargarGasolina(int pos) throws InterruptedException {
            int gasolina = 0;
            while (gasolina < 3000) {
                mutex.acquire();  //adquirimos el semáforo de variables
                n_repost_g++;     //aumentamos el número de barcos repostando

                if (n_repost_g == 1)
                    reponedor.acquire(); //si solo hay uno pedimos el permiso para adquirir la prioridad de reponer
                mutex.release();//liberamos el semáforo de variables compartidas

                if (listaTanques[pos].descargar()) {
                    System.out.println("Tanque "+pos+" "+listaTanques[pos].getLitros());
                    gasolina += 250;
                    System.out.println("Petrolero "+pos+": "+gasolina);
                }
                mutex.acquire();//lo admquirimos para restar y comprobar
                n_repost_g--; //disminuimos el úmero de barcos repostando
                if (n_repost_g == 0)//adquirimos mutex para modificar y si ya no hay barcos repostando liberamos
                    reponedor.release();
                mutex.release();
            }
        }


        @Override
        public void run() {
            try {
                cargarGasolina(pos);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * thread encargado de cargar agua en el barco cogiendo de 1000 en 1000 hasta que tenga 5000
     * antes de intentar cojer agua pregunta si el tanque está ocupado y espera hasta que esté libre
     */
    private class Thread_Agua extends Thread{

        int pos;

        Thread_Agua(int pos){
            this.pos = pos;
        }

        public void cargarAgua(){
            try {
                int litros = 0;

                while (litros < 5000){
                    tanqueAgua.acquire();
                    litros += 1000;
                   // System.out.println("tanqueAgua "+pos+": " + litros);
                    tanqueAgua.release();
                 /* esto se activa en el caso de que queramos ver que funciona realmente
                    try {
                        sleep(50);                 //1000 milliseconds is one second.
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }*/
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


        @Override
        public void run() {
            cargarAgua();
        }


    }


}
