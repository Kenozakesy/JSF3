package sample.calculate;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by Gebruiker on 2-10-2017.
 */
public class MyCallable implements Observer, Callable<ArrayList<Edge>>
{
    private ArrayList<Edge> edges = new ArrayList<>();
    private KochFractal koch;
    //private int level = 1;      // The current level of the fractal
    //private int nrOfEdges = 3;  // The number of edges in the current level of the fractal
    boolean finished = false;
    private int edge;
    KochManager kochManager;

    @Override
    public ArrayList<Edge> call()
    {
        switch (edge){
            case 1:
                koch.generateLeftEdge();
                break;
            case 2 :
                koch.generateRightEdge();
                break;
            case 3:
                koch.generateBottomEdge();
                break;
                default: break;
        }

        return edges;
    }

    MyCallable(KochFractal koch, KochManager kochManager, int edge)
    {
        this.koch = koch;
        this.kochManager = kochManager;
        this.edge = edge;
        this.koch.addObserver(this);
    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        edges.add((Edge)arg);
    }

}
