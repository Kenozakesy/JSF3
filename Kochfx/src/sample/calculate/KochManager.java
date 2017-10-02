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
    public int count;

    public KochManager(JSF31KochFractalFX application)
    {
        this.application = application;
        this.koch = new KochFractal();
        this.koch.addObserver(this);
    }

    public synchronized void drawEdges(){
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

        EdgeGenerator edgeGeneratorLeft = new EdgeGenerator(koch.getLevel(),koch.getNrOfEdges(),this,1);
        Thread thread = new Thread(edgeGeneratorLeft, "EdgeGeneratorThread");
        edgeGeneratorLeft.addObserver(this);

        EdgeGenerator edgeGeneratorRight = new EdgeGenerator(koch.getLevel(),koch.getNrOfEdges(),this,2);
        Thread thread2 = new Thread(edgeGeneratorRight, "EdgeGeneratorThread2");
        edgeGeneratorRight.addObserver(this);

        EdgeGenerator edgeGeneratorBottom = new EdgeGenerator(koch.getLevel(),koch.getNrOfEdges(),this,3);
        Thread thread3 = new Thread(edgeGeneratorBottom, "EdgeGeneratorThread3");
        edgeGeneratorBottom.addObserver(this);

        thread.start();
        thread2.start();
        thread3.start();

        thread.interrupt();
        thread2.interrupt();
        thread3.interrupt();

        timeStamp.setEnd("Ending generating");
        application.setTextCalc(timeStamp.toString());


        TimeStamp stamp2 = new TimeStamp();
        stamp2.setBegin("begin draw method");

        drawEdges(); //draw edges

        stamp2.setEnd("end drawing");
        application.setTextDraw(stamp2.toString());
    }

    public synchronized void increaseCount()
    {
        count++;

        if (count >= 3){
            application.requestDrawEdges();
            count = 0;
        }
    }

    @Override
    public synchronized void update(Observable o, Object arg)
    {
        edgeList.add((Edge)arg);
    }
}
