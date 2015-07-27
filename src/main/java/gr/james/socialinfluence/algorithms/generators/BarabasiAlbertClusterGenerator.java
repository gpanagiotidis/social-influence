package gr.james.socialinfluence.algorithms.generators;

import gr.james.socialinfluence.api.Graph;
import gr.james.socialinfluence.api.GraphGenerator;
import gr.james.socialinfluence.graph.GraphOperations;
import gr.james.socialinfluence.graph.Vertex;
import gr.james.socialinfluence.util.Finals;

public class BarabasiAlbertClusterGenerator<T extends Graph> implements GraphGenerator<T> {
    private Class<T> type;
    private int totalVertices;
    private int initialClique;
    private int stepEdges;
    private double a;
    private int clusters;

    public BarabasiAlbertClusterGenerator(Class<T> type, int totalVertices, int initialClique, int stepEdges, double a, int clusters) {
        this.type = type;
        this.totalVertices = totalVertices;
        this.initialClique = initialClique;
        this.stepEdges = stepEdges;
        this.a = a;
        this.clusters = clusters;
    }

    @Override
    public T create() {
        Graph[] c = new Graph[clusters];

        GraphGenerator scaleFreeGenerator = new BarabasiAlbertGenerator<>(type, totalVertices, stepEdges, initialClique, a);
        for (int i = 0; i < clusters; i++) {
            c[i] = scaleFreeGenerator.create();
        }

        Vertex[] randomVertices = new Vertex[clusters];
        for (int i = 0; i < clusters; i++) {
            randomVertices[i] = c[i].getRandomVertex();
        }

        T g = GraphOperations.combineGraphs(type, c);

        for (int i = 0; i < clusters; i++) {
            Vertex s = randomVertices[i];
            Vertex t = randomVertices[(i + 1) % clusters];
            g.addEdge(s, t, true);
        }

        g.setMeta(Finals.DEFAULT_TYPE_META, "BarabasiAlbertCluster")
                .setMeta("totalVertices", String.valueOf(totalVertices))
                .setMeta("initialClique", String.valueOf(initialClique))
                .setMeta("stepEdges", String.valueOf(stepEdges))
                .setMeta("a", String.valueOf(a))
                .setMeta("clusters", String.valueOf(clusters));

        return g;
    }
}
