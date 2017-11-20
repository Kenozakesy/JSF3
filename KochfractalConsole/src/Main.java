import WriteReader.WriteReader;
import calculate.Edge;
import calculate.KochFractal;
import calculate.KochManager;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        KochManager manager = new KochManager();
        manager.changeLevel(1);


        WriteReader WR = new WriteReader();
        ArrayList<Edge> edges = WR.getEdges();

        int tel = 0;
        for (Edge E: edges) {
           tel++;
        }
        System.out.println(tel);


    }
}
