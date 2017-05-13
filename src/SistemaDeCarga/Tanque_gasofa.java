package SistemaDeCarga;

/**
 * Created by trjano on 2/03/17.
 */
public class Tanque_gasofa {

    int litros;

    public Tanque_gasofa(){
        litros = 1000;
    }

    public boolean descargar(){
        if(litros > 0) {
            litros -= 250;
            return  true;
        }
        else return false;
    }

    public void rellenar(){
        litros = 1000;
    }

    public int getLitros() {
        return litros;
    }

    public boolean estaVacio(){
        return  litros <= 0;
    }
}
