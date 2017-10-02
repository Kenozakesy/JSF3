package sample.calculate;

import javafx.scene.paint.Color;

import java.util.Observable;

/**
 * Created by Jordi on 30-9-2017.
 */
public class EdgeGenerator extends Observable implements Runnable{

    private int level = 1;      // The current level of the fractal
    private int nrOfEdges = 3;  // The number of edges in the current level of the fractal
    private float hue;          // Hue value of color for next edge
    private boolean cancelled;  // Flag to indicate that calculation has been cancelled
    boolean finished = false;
    private int edge;
    KochManager kochManager;

    @Override
    public void run()
    {
        while(true){

            switch (edge){
                case 1:
                    generateLeftEdge();
                    break;
                case 2 :
                    generateRightEdge();
                    break;
                case 3:
                    generateBottomEdge();
                    break;
            }

            if (Thread.currentThread().isInterrupted() && this.finished)
            {
                break;
            }

        }
    }

    EdgeGenerator(int level, int nrOfEdges, KochManager kochManager, int edge)
    {
        this.level = level;
        this.nrOfEdges = nrOfEdges;
        this.kochManager = kochManager;
        this.edge = edge;
    }

    private void drawKochEdge(double ax, double ay, double bx, double by, int n) {
        if (!cancelled) {
            if (n == 1) {
                hue = hue + 1.0f / nrOfEdges;
                Edge e = new Edge(ax, ay, bx, by, Color.hsb(hue*360.0, 1.0, 1.0));
                this.setChanged();
                this.notifyObservers(e);
                boolean sd;
            }
            else {
                double angle = Math.PI / 3.0 + Math.atan2(by - ay, bx - ax);
                double distabdiv3 = Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay)) / 3;
                double cx = Math.cos(angle) * distabdiv3 + (bx - ax) / 3 + ax;
                double cy = Math.sin(angle) * distabdiv3 + (by - ay) / 3 + ay;
                final double midabx = (bx - ax) / 3 + ax;
                final double midaby = (by - ay) / 3 + ay;

                boolean ss;
                drawKochEdge(ax, ay, midabx, midaby, n - 1);
                drawKochEdge(midabx, midaby, cx, cy, n - 1);
                drawKochEdge(cx, cy, (midabx + bx) / 2, (midaby + by) / 2, n - 1);
                drawKochEdge((midabx + bx) / 2, (midaby + by) / 2, bx, by, n - 1);
            }
                finished = true;

        }
    }

    synchronized void generateLeftEdge() {
        if (!finished){

            hue = 0f;
            cancelled = false;
            drawKochEdge(0.5, 0.0, (1 - Math.sqrt(3.0) / 2.0) / 2, 0.75, level);
            kochManager.increaseCount();
        }
    }

    synchronized void generateBottomEdge() {
        if (!finished){
            hue = 1f / 3f;
            cancelled = false;
            drawKochEdge((1 - Math.sqrt(3.0) / 2.0) / 2, 0.75, (1 + Math.sqrt(3.0) / 2.0) / 2, 0.75, level);
            kochManager.increaseCount();
        }
    }

    synchronized void generateRightEdge() {
        if(!finished){
            hue = 2f / 3f;
            cancelled = false;
            drawKochEdge((1 + Math.sqrt(3.0) / 2.0) / 2, 0.75, 0.5, 0.0, level);
            kochManager.increaseCount();
        }
    }

}
