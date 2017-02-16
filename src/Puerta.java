/**
 * Created by trjano on 14/02/17.
 */

/**
 * Implementamos un singleton porque solo habrá una puerta
 */
public class Puerta {

    private static Puerta instance = null;
    TorreDeControl torre;

    private Puerta(){
        torre = new TorreDeControl();
    }


    public static Puerta getInstance(){
        if(instance == null)
            instance = new Puerta();
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
        torre.finSalida();

    }
}
