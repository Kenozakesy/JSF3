/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package calculate;

import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author Peter Boots
 */
public class Edge implements Serializable {

    public double X1, Y1, X2, Y2;
    //trnasient moet erbij staan
    public transient Color color;
    
    public Edge(double X1, double Y1, double X2, double Y2, Color color) {
        this.X1 = X1;
        this.Y1 = Y1;
        this.X2 = X2;
        this.Y2 = Y2;
        this.color = color;
    }

}
