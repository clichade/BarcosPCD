package DescargaContenedores;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by trjano on 15/03/17.
 */
public class Plataforma {

    /**
     * Enumerador que contiene los tipos de contenedores y a la vez el estado de la base
     */
    public enum Contenedor {
        VACIO, SAL, HARINA, AZUCAR
    }

    /**
     * Inicio de la definicion de condiciones y de lock
     */
    Lock monitor = new ReentrantLock();
    Condition barco = monitor.newCondition();
    Condition cogerSal = monitor.newCondition();
    Condition cogerHarina = monitor.newCondition();
    Condition cogerAzucar = monitor.newCondition();
    /**
     * Fin de la definición de condiciones y el lock
     */

    Grua gruaSal;
    Grua gruaHarina;
    Grua gruaAzucar;
    GruaBarco gruaBarco;

    //La base de la plataforma que informará de que contiene un contenedor de x tipo o nada(VACIO)
    Contenedor base;


    /**
     * constructor por defecto inicaliza la base a vacio e instancia las gruas
     */
    public Plataforma() {
        base = Contenedor.VACIO;
        gruaSal = new Grua(Contenedor.SAL);
        gruaAzucar = new Grua(Contenedor.AZUCAR);
        gruaHarina = new Grua(Contenedor.HARINA);
    }

    /**
     * en el momento en el que llegu eel barco mercante se instancia su grua y se empieza a procesar
     */
    public void encallar() {
        gruaBarco = new GruaBarco();
        procesar();

    }

    /**
     * startea todos los trheads
     */
    private void procesar() {
        gruaBarco.start();
        gruaSal.start();
        gruaHarina.start();
        gruaAzucar.start();

    }

    /**
     * el thread perteneciente al barco el cual contiene la lista de contenedores
     */
    private class GruaBarco extends Thread {

        LinkedList<Contenedor> listaCont;

        /**
         * inicializa la lsita de contenedores
         */
        public GruaBarco() {
            listaCont = new LinkedList<>();

            for (int i = 0; i < 12; i++)
                listaCont.add(Contenedor.AZUCAR);
            for (int i = 0; i < 20; i++)
                listaCont.add(Contenedor.SAL);
            for (int i = 0; i < 5; i++)
                listaCont.add(Contenedor.HARINA);
        }

        public void descargar() {
            monitor.lock();
            while (base != Contenedor.VACIO) {
                try {
                    barco.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            base = listaCont.removeFirst();//eliminamos el primero y lo pasamos a la base
            //dependiendo del contenido de la base ahora llama a una grua o a otra
            System.out.println("El barco ha soltado " + base);

            switch (base) {
                case AZUCAR:
                    cogerAzucar.signal();
                    System.out.println("El barco ha avisado a grua " + base);
                    break;
                case HARINA:
                    cogerHarina.signal();
                    System.out.println("El barco ha avisado a grua " + base);
                    break;
                case SAL:
                    cogerSal.signal();
                    System.out.println("El barco ha avisado a grua " + base);
                    break;

                default:
                    System.out.println("Error de algun tipo en el barco");
            }
            monitor.unlock();

        }

        @Override
        public void run() {
            while (!listaCont.isEmpty())
                descargar();
        }
    }

    /**
     * thread grua perteneciente a la estación, dependiendo del tipo de contenedor la grua reaccionará
     * ante una condicion u otra
     */
    private class Grua extends Thread {
        Contenedor tipo;
        Condition condGrua;
        int n;

        public Grua(Contenedor cont) {
            n = 0;
            switch (cont) {
                case SAL:
                    tipo = Contenedor.SAL;
                    condGrua = cogerSal;
                    n = 20;
                    break;
                case HARINA:
                    tipo = Contenedor.HARINA;
                    condGrua = cogerHarina;
                    n = 5;
                    break;
                case AZUCAR:
                    tipo = Contenedor.AZUCAR;
                    condGrua = cogerAzucar;
                    n = 12;
            }
        }

        private void coger() {
            monitor.lock();

            while (base == Contenedor.VACIO) {
                try {
                    System.out.println("La grua " + tipo + " PRETENDE");
                    condGrua.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            //cogemos el contenedor y ponemos la base a vacio
            if (base == tipo) {
                base = Contenedor.VACIO;
                n--;
                System.out.println("La grua " + tipo + " COGIÓ");
                barco.signal();
            }
            monitor.unlock();
        }

        @Override
        public void run() {
            while (n>0)
                coger();
        }
    }


}
