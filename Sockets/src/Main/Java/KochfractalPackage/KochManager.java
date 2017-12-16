package KochfractalPackage;



import java.awt.*;
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

    public ArrayList<Edge> changeLevel(int nxt) {
        koch.setLevel(nxt);
        edgeList.clear();

        koch.generateLeftEdge();
        koch.generateBottomEdge();
        koch.generateRightEdge();

        return edgeList;
    }

    @Override
    public void update(Observable o, Object arg) {
        edgeList.add((Edge) arg);
    }
}
