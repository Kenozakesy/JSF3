package Notifier;

import WriteReader.Reader;
import calculate.KochData;

import java.io.IOException;

/**
 * Created by Gebruiker on 4-12-2017.
 */
public class NotifyRead {

    public NotifyRead()
    {

    }

    public static void readMessage()
    {
        Reader reader = new Reader();
        KochData kd = null;
        try {

            //binary
            //kd = reader.readBinaryBuffered();
            //kd = reader.readBinaryNotBuffered();

            //text
            kd = reader.readTextBuffered();
            //kd = reader.readTextNotBuffered();

            //mapped
            //kd = reader.readEdgesMapped();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(kd.getEdges().size());
        System.out.println
                (
                        String.valueOf(kd.getEdges().get(0).X1) + "\n" +
                                String.valueOf(kd.getEdges().get(2).X1) + "\n" +
                                String.valueOf(kd.getEdges().get(kd.getEdges().size() - 1).X1)
                );


    }
}
