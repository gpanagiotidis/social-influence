package gr.james.socialinfluence.graph.generators;

import gr.james.socialinfluence.api.Graph;
import gr.james.socialinfluence.api.GraphGenerator;
import gr.james.socialinfluence.graph.Vertex;
import gr.james.socialinfluence.helper.Helper;

public class CycleGenerator<T extends Graph> implements GraphGenerator<T> {
    private Class<T> type;
    private int totalVertices;

    private T g;

    public CycleGenerator(Class<T> type, int totalVertices) {
        this.type = type;
        this.totalVertices = totalVertices;
    }

    private void reset() {
        g = Helper.instantiateGeneric(type);
        g.setMeta("name", "cycle")
                .setMeta("totalVertices", String.valueOf(totalVertices));
    }

    @Override
    public T create() {
        reset();

        Vertex startVertex = g.addVertex(), previousVertex = startVertex;
        while (g.getVerticesCount() < totalVertices) {
            Vertex newVertex = g.addVertex();
            g.addEdge(previousVertex, newVertex, true);
            previousVertex = newVertex;
        }
        g.addEdge(startVertex, previousVertex, true);

        return g;
    }
}