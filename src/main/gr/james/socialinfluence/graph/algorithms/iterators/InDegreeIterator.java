package gr.james.socialinfluence.graph.algorithms.iterators;

import gr.james.socialinfluence.graph.Graph;
import gr.james.socialinfluence.graph.Vertex;
import gr.james.socialinfluence.helper.RandomHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class InDegreeIterator implements Iterator<Vertex> {
    private Map<Vertex, Integer> nodes;

    public InDegreeIterator(Graph g) {
        nodes = new HashMap<>(g.getInDegree());
    }

    @Override
    public boolean hasNext() {
        return this.nodes.size() > 0;
    }

    @Override
    public Vertex next() {
        Integer maxDegree = 0;

        for (Map.Entry<Vertex, Integer> e : this.nodes.entrySet()) {
            if (e.getValue() > maxDegree) {
                maxDegree = e.getValue();
            }
        }

        ArrayList<Vertex> maxVertices = new ArrayList<>();
        for (Map.Entry<Vertex, Integer> e : this.nodes.entrySet()) {
            if (e.getValue().equals(maxDegree)) {
                maxVertices.add(e.getKey());
            }
        }

        Vertex maxVertex = maxVertices.get(RandomHelper.getRandom().nextInt(maxVertices.size()));
        this.nodes.remove(maxVertex);
        return maxVertex;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}