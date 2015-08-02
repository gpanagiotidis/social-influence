package gr.james.socialinfluence.graph;

import gr.james.socialinfluence.api.Graph;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * <p>Unmodifiable decorator of a {@link Graph}.</p>
 */
public class ImmutableGraph implements Graph {
    // TODO: Edge has setWeight method, which can be used to change the graph
    private Graph g;

    /**
     * <p>Construct a new {@code ImmutableGraph} from a given {@code Graph} g.</p>
     *
     * @param g the {@code Graph} to decorate
     */
    public ImmutableGraph(Graph g) {
        this.g = Objects.requireNonNull(g);
    }

    @Override
    public String getMeta(String key) {
        return this.g.getMeta(key);
    }

    @Override
    public Graph setMeta(String key, String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Graph clearMeta() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<Vertex, Edge> getOutEdges(Vertex v) {
        return this.g.getOutEdges(v);
    }

    @Override
    public Map<Vertex, Edge> getInEdges(Vertex v) {
        return this.g.getInEdges(v);
    }

    @Override
    public Set<Vertex> getVertices() {
        return this.g.getVertices();
    }

    @Override
    public List<Vertex> getVerticesAsList() {
        return this.g.getVerticesAsList();
    }

    @Override
    public boolean addVertex(Vertex v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Graph removeVertex(Vertex v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Edge addEdge(Vertex source, Vertex target) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Graph removeEdge(Vertex source, Vertex target) {
        throw new UnsupportedOperationException();
    }
}
