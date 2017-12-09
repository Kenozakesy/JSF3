package calculate;


import WriteReader.Writer;
import timeutil.TimeStamp;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Koen on 18-9-2017.
 */
public class KochManager implements Observer {


    KochFractal koch;
    TimeStamp timeStamp = new TimeStamp();
    ArrayList<Edge> edgeList = new ArrayList<>();

    public KochManager() {
        this.koch = new KochFractal();
        this.koch.addObserver(this);
    }

    public void changeLevel(int nxt) {
        koch.setLevel(nxt);
        edgeList.clear();

        koch.generateLeftEdge();
        koch.generateBottomEdge();
        koch.generateRightEdge();


        Writer writer = new Writer();

        //write binary buffered
        //writer.writeEdgesToBinaryBufferd(edgeList);

        //write binary not buffered
        //writer.writeEdgesToBinaryNotBufferd(edgeList);

        //write text buffered
        writer.writeEdgesToTextBuffered(edgeList);

        //write text not buffered
        //writer.writeEdgesToTextNotBuffered(edgeList);

        //write mappedfile
        //writer.writeEdgesMapped(edgeList);

    }

    @Override
    public void update(Observable o, Object arg) {
        edgeList.add((Edge) arg);
    }
}
