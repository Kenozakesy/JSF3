package WriteReader;

import calculate.Edge;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Gebruiker on 20-11-2017.
 */
public class BufferedWriteReader {

    FileOutputStream fot = null;
    ObjectOutputStream oos = null;

    //moet buffered worden
    public BufferedWriteReader()
    {
        try {
            fot = new FileOutputStream("C:\\Users\\Gebruiker\\Documents\\IntelliJProjects\\KochfractalConsole\\Files\\EdgesWithBuffer.txt");

            //BufferedReader br=new BufferedOutputStream(fot, 50);

            oos = new ObjectOutputStream(fot);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serializeAddress(ArrayList<Edge> edgelist) {

        try {
            for (Edge E: edgelist)
            {
                oos.writeObject(E);
            }
            System.out.println("Done");

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

            if (fot != null) {
                try {
                    fot.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
