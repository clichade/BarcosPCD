/**
 * Created by trjano on 14/02/17.
 */
public class Puerta {

    private static Puerta instance = null;

    private Puerta(){
    }


    public static Puerta getInstance(){
        if(instance == null)
            instance = new Puerta();
        return  instance;
    }

    public void entrar(int id){
        System.out.println("El barco "+id+" va a entrar");
        System.out.println("El barco "+id+" va a entrar");
        System.out.println("El barco "+id+" va a entrar");

    }

    public void salir(int id){
        System.out.println("El barco "+id+" va a salir");
        System.out.println("El barco "+id+" va a salir");
        System.out.println("El barco "+id+" va a salir");

    }
}
