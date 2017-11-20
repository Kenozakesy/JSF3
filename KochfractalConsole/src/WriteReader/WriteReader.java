package WriteReader;

import calculate.Edge;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Gebruiker on 20-11-2017.
 */
public class WriteReader {

    FileOutputStream fot = null;
    ObjectOutputStream oos = null;

    FileInputStream fit = null;
    ObjectInputStream ois = null;

    public WriteReader() {
        try {
            fot = new FileOutputStream("C:\\Users\\Gebruiker\\Documents\\IntelliJProjects\\KochfractalConsole\\Files\\Edges.txt");
            oos = new ObjectOutputStream(fot);

            fit = new FileInputStream("C:\\Users\\Gebruiker\\Documents\\IntelliJProjects\\KochfractalConsole\\Files\\Edges.txt");
            ois = new ObjectInputStream(fit);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serializeEdge(ArrayList<Edge> edgelist) {

        try {
            for (Edge E: edgelist)
            {
                oos.writeObject(E);
                oos.flush(); //doet niets voor zover ik weet
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

    public ArrayList<Edge> getEdges()
    {
        ArrayList<Edge> edgelist = new ArrayList<>();

        try {

            Edge obj = null;
            while ((obj = (Edge) ois.readObject()) != null) {  //???????????????????
                edgelist.add(obj);
            }

            System.out.println("Done");

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

            if (fit != null) {
                try {
                    fot.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (ois != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }








        return  edgelist;
    }

}
