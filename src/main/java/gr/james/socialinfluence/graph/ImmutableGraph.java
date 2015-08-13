package gr.james.socialinfluence.graph;

import gr.james.socialinfluence.api.Graph;
import gr.james.socialinfluence.util.Conditions;
import gr.james.socialinfluence.util.collections.VertexPair;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>Unmodifiable decorator of a {@link Graph}.</p>
 */
public final class ImmutableGraph implements Graph {
    private Graph g;

    private ImmutableGraph(Graph g) {
        this.g = Conditions.requireNonNull(g);
    }

    public static ImmutableGraph decorate(Graph g) {
        return new ImmutableGraph(g);
    }

    @Override
    public String getMeta(String key) {
        return this.g.getMeta(key);
    }

    @Override
    public void setMeta(String key, String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clearMeta() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getGraphType() {
        return this.g.getGraphType();
    }

    @Override
    public void setGraphType(String type) {
        this.g.setGraphType(type);
    }

    @Override
    public Iterator<Vertex> iterator() {
        return this.g.iterator();
    }

    @Override
    public boolean containsVertex(Vertex v) {
        return this.g.containsVertex(v);
    }

    @Override
    public boolean containsEdge(Vertex source, Vertex target) {
        return this.g.containsEdge(source, target);
    }

    @Override
    public Edge findEdge(Vertex source, Vertex target) {
        return this.g.findEdge(source, target);
    }

    @Override
    public Vertex getVertexFromIndex(int index) {
        return this.g.getVertexFromIndex(index);
    }

    @Override
    public Vertex getRandomVertex() {
        return this.g.getRandomVertex();
    }

    @Override
    public Map<VertexPair, Edge> getEdges() {
        return this.g.getEdges();
    }

    @Override
    public int getEdgesCount() {
        return this.g.getEdgesCount();
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
    public double getOutStrength(Vertex v) {
        return this.g.getOutStrength(v);
    }

    @Override
    public double getInStrength(Vertex v) {
        return this.g.getInStrength(v);
    }

    @Override
    public int getOutDegree(Vertex v) {
        return this.g.getOutDegree(v);
    }

    @Override
    public int getInDegree(Vertex v) {
        return this.g.getInDegree(v);
    }

    @Override
    public boolean isUndirected() {
        return this.g.isUndirected();
    }

    @Override
    public List<Vertex> getVertices() {
        return this.g.getVertices();
    }

    @Override
    public int getVerticesCount() {
        return this.g.getVerticesCount();
    }

    @Override
    public Vertex getRandomOutEdge(Vertex from, boolean weighted) {
        return this.g.getRandomOutEdge(from, weighted);
    }

    @Override
    public double getDiameter() {
        return this.g.getDiameter();
    }

    @Override
    public Vertex addVertex() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addVertex(Vertex v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Vertex> addVertices(int count) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeVertex(Vertex v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeVertices(Collection<Vertex> vertices) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addEdges(Collection<Vertex> among) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addEdges(Vertex... among) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Edge addEdge(Vertex source, Vertex target) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeEdge(Vertex source, Vertex target) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeEdges(Collection<Vertex> among) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeEdges(Vertex... among) {
        throw new UnsupportedOperationException();
    }
}
