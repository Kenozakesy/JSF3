package sample.calculate;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import sample.timeutil.TimeStamp;
import sample.jsf31kochfractalfx.*;

/**
 * Created by Jordi on 18-9-2017.
 */
public class KochManager implements Observer {

    KochFractal koch;
    JSF31KochFractalFX application;
    ArrayList<Edge> edgeList = new ArrayList<>();

    public KochManager(JSF31KochFractalFX application)
    {
        this.application = application;
        this.koch = new KochFractal();
        this.koch.addObserver(this);
    }

    public void drawEdges(){
        application.clearKochPanel();

        int i = 0;
        TimeStamp timeStamp = new TimeStamp();
        timeStamp.setBegin("Starting the drawing");

        for (Edge edge : edgeList)
        {
            application.drawEdge(edge);
            i++;
        }

        timeStamp.setEnd("Ending the drawing");
        application.setTextDraw(timeStamp.toString());
        application.setTextNrEdges(String.valueOf(i));
    }

    public void changeLevel(int nxt)
    {
        koch.setLevel(nxt);
        edgeList.clear();
        TimeStamp timeStamp = new TimeStamp();
        timeStamp.setBegin("Starting generating");
        koch.generateLeftEdge();
        koch.generateBottomEdge();
        koch.generateRightEdge();
        timeStamp.setEnd("Ending generating");
        application.setTextCalc(timeStamp.toString());
        drawEdges();
    }

    @Override
    public void update(Observable o, Object arg)
    {
        edgeList.add((Edge)arg);
    }
}
