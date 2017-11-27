import WriteReader.Reader;
import WriteReader.Writer;
import calculate.Edge;
import calculate.KochData;
import calculate.KochManager;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        KochManager manager = new KochManager();
        manager.changeLevel(7);

        Reader reader = new Reader();
        KochData kd = null;
        try {

            //binary
            //kd = reader.readBinaryBuffered();
            //kd = reader.readBinaryNotBuffered();

            //text
            //kd = reader.readTextBuffered();
            //kd = reader.readTextNotBuffered();

            //mapped
            kd = reader.readEdgesMapped();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(kd.getEdges().size());
        System.out.println
                (
                        String.valueOf(kd.getEdges().get(0).X1) + "\n" +
                                String.valueOf(kd.getEdges().get(2).X1) +  "\n" +
                                String.valueOf(kd.getEdges().get(kd.getEdges().size() - 1).X1)
                );
    }
}
