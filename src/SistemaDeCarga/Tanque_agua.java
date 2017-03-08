import java.util.concurrent.*;

/**
 * Created by trjano on 2/03/17.
 */
public class Tanque_agua {


    Semaphore tanqueAgua = new Semaphore(1);



    public void administrarAgua(Barco b) throws InterruptedException {
        int litros = 0;
        while (litros < 5000){
            tanqueAgua.acquire();
            litros += 1000;
            tanqueAgua.release();
        }

    }
}
