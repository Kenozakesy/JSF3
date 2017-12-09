import Notifier.NotifyThread;
import WriteReader.Reader;
import calculate.KochData;
import calculate.KochManager;

import java.io.IOException;


public class Write {

    public static void main(String[] args) {

        new Thread(new NotifyThread()).start();

        KochManager manager = new KochManager();
        manager.changeLevel(3);

    }


}
