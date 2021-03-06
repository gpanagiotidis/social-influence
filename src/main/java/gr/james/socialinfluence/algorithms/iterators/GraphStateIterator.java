package gr.james.socialinfluence.algorithms.iterators;

import gr.james.socialinfluence.api.GraphState;
import gr.james.socialinfluence.graph.Vertex;
import gr.james.socialinfluence.util.collections.Weighted;

import java.util.Collections;
import java.util.Iterator;
import java.util.PriorityQueue;

public class GraphStateIterator<T extends Comparable<T>> implements Iterator<Weighted<Vertex, T>> {
    protected PriorityQueue<Weighted<Vertex, T>> p;

    public GraphStateIterator(GraphState<T> state) {
        p = new PriorityQueue<>(state.size(), Collections.reverseOrder());
        for (Vertex v : state.keySet()) {
            p.add(new Weighted<>(v, state.get(v)));
        }
    }

    @Override
    public boolean hasNext() {
        return !p.isEmpty();
    }

    @Override
    public Weighted<Vertex, T> next() {
        return p.poll();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
