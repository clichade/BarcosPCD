/**
 * Created by trjano on 14/02/17.
 */
public class Barco implements Runnable {

    int id;
    boolean entra;

    Barco(int id, boolean entra){
        this.id = id;
        this.entra = entra;
    }

    public void moverse (){
        if(entra)
            Puerta.entrar(id);
        else
            Puerta.salir(id);
    }

    @Override
    public void run() {
        moverse();
    }
}
