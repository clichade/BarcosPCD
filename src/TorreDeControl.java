/**
 * Created by trjano on 16/02/17.
 */
public class TorreDeControl {

    private  int entrando;
    private  int saliendo;

    public TorreDeControl(){
        entrando = 0;
        saliendo = 0;
    }

    /**
     * para que entre un barco no puede haber ningun otro saliendo
     */
    public synchronized void permEntrada() {
    try {
        while (saliendo > 0)
            wait();
    }catch (InterruptedException e) {
    }

        entrando ++;
    }

    /**
     * para que salga un barco no debe haber ingun otro entrando
     */
    public synchronized void permSalida(){
        try {
            while (entrando > 0)
                wait();
        }catch (InterruptedException e) {
        }

        saliendo ++;
    }

    public synchronized void finEntrada(){
        entrando --;
        if(entrando == 0)
        notifyAll();
    }
    public synchronized void finSalida(){

        saliendo --;
        if (saliendo == 0)
        notifyAll();
    }
}
