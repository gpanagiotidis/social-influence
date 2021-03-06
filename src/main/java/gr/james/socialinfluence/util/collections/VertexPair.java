package gr.james.socialinfluence.util.collections;

import gr.james.socialinfluence.graph.Vertex;

/**
 * <p>A {@link Pair} of {@link Vertex vertices}.</p>
 */
public class VertexPair extends Pair<Vertex> {
    public VertexPair(Vertex source, Vertex target) {
        super(source, target);
    }

    public Vertex getSource() {
        return this.getFirst();
    }

    public Vertex getTarget() {
        return this.getSecond();
    }
}
