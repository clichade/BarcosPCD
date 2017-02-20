/**
 * Created by trjano on 14/02/17.
 */

import javax.swing.plaf.synth.SynthOptionPaneUI;

/**
 * Implementamos un singleton porque solo habrá una puerta
 */
public class Puerta {
//--------------------SINGLETON---------------------
    private static Puerta instance = new Puerta();
    TorreDeControl torre;

    private Puerta(){
        torre = new TorreDeControl();
    }


    public static Puerta getInstance(){
        return  instance;
    }

    /**
     * para entrar primero no debe haber nadie saliendo
     * @param id
     */
    public void entrar(int id){
        torre.permEntrada();
        System.out.println("El barco "+id+" va a entrar");
        System.out.println("El barco "+id+" va a entrar");
        System.out.println("El barco "+id+" va a entrar");
        System.out.println("El barco "+id+" finalizará  su entrada");
        torre.finEntrada();

    }

    /**
     * para salir no debe haber nadie entrando
     * @param id
     */
    public void salir(int id){
        torre.permSalida();
        System.out.println("El barco "+id+" va a salir");
        System.out.println("El barco "+id+" va a salir");
        System.out.println("El barco "+id+" va a salir");
        System.out.println("El barco "+id+" finalizará  su salida");
        torre.finSalida();

    }
}
