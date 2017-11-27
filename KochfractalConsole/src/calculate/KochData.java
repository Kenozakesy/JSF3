package calculate;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Gebruiker on 22-11-2017.
 */
public class KochData implements Serializable {

    private ArrayList<Edge> edges = new ArrayList<>();

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public KochData(ArrayList<Edge> edges)
    {
        this.edges.addAll(edges);
    }
}
