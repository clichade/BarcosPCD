/**
 * Created by trjano on 2/03/17.
 */
public class Tanque_gasofa {

    int litros;

    public Tanque_gasofa(){
        litros = 1000;
    }

    public void descargar(){
        if(litros > 0)
            litros -= 250;
    }

    public void rellenar(){
        litros = 1000;
    }

    public int getLitros() {
        return litros;
    }
}
