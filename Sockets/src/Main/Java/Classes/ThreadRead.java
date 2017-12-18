package Classes;

import KochfractalPackage.Edge;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by Gebruiker on 16-12-2017.
 */
public class ThreadRead implements Runnable {

    private ObjectInputStream in;

    public ThreadRead(ObjectInputStream in)
    {
        this.in = in;
    }

    @Override
    public void run() {

        int x = 0;
        while (x < 4)
        {
            try {
                Edge data = (Edge)in.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            x++;
        }

    }
}
