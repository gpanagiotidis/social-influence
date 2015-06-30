package gr.james.socialinfluence.collections;

import gr.james.socialinfluence.graph.Graph;
import gr.james.socialinfluence.graph.Vertex;
import gr.james.socialinfluence.helper.Finals;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>GraphState maps every vertex in a graph with a double value. Useful as a holder for some metric, like PageRank.
 * This class also contains useful methods for statistical analysis of that metric.</p>
 */
public class GraphState extends HashMap<Vertex, Double> {
    // TODO: This class doesn't seem right, constructor accepting graph? maybe convert to subclass of HashMap
    // TODO: GraphState<E> extends HashMap<Vertex, E>?

    public GraphState() {
    }

    public GraphState(Graph g) {
        for (Vertex v : g.getVertices()) {
            this.put(v, Finals.DEFAULT_GRAPH_STATE);
        }
    }

    public GraphState(Graph g, double initialState) {
        for (Vertex v : g.getVertices()) {
            this.put(v, initialState);
        }
    }

    public GraphState subtractAbs(GraphState r) {
        GraphState ret = new GraphState();
        for (Map.Entry<Vertex, Double> e : this.entrySet()) {
            ret.put(e.getKey(),
                    Math.abs(e.getValue() - r.get(e.getKey()))
            );
        }
        return ret;
    }

    /**
     * <p>Checks if all values in this object are less than or equal a specified value.</p>
     * <p><b>Running Time:</b> Fast</p>
     *
     * @param e which value to check against
     * @return true if all values are less than e, otherwise false
     */
    public boolean lessThan(double e) {
        boolean ret = true;
        for (double k : this.values()) {
            if (k > e) {
                ret = false;
                break;
            }
        }
        return ret;
    }

    /**
     * <p>Performs a deep copy on this instance. Vertices will not be deep copied.
     * Instead they will keep their references.</p>
     * <p><b>Running Time:</b> Fast</p>
     *
     * @return the deep copy of this instance
     */
    public GraphState deepCopy() {
        GraphState tmpState = new GraphState();
        for (Map.Entry<Vertex, Double> it : this.entrySet()) {
            tmpState.put(it.getKey(), it.getValue());
        }
        return tmpState;
    }

    public double getMean() {
        return getMean(this.keySet());
    }

    public double getMean(Set<Vertex> includeOnly) {
        double mean = 0.0;
        int count = 0;

        for (Map.Entry<Vertex, Double> e : this.entrySet()) {
            if (includeOnly.contains(e.getKey())) {
                mean += e.getValue();
                count++;
            }
        }

        return mean / (double) count;
    }

    public double getSum() {
        double sum = 0.0;
        for (double e : this.values()) {
            sum += e;
        }
        return sum;
    }

    /*@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        GraphState other = (GraphState) obj;
        if (state == null) {
            if (other.state != null) {
                return false;
            }
        } else if (!state.equals(other.state)) {
            return false;
        }
        return true;
    }*/
}