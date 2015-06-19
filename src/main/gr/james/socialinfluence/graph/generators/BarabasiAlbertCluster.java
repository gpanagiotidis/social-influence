package gr.james.socialinfluence.graph.generators;

import gr.james.socialinfluence.graph.Graph;
import gr.james.socialinfluence.graph.Vertex;

import java.util.Random;

public class BarabasiAlbertCluster {
    public static Graph generate(int totalVertices, int initialClique, int stepEdges, double a, int clusters, Random R) {
        Graph[] c = new Graph[clusters];

        for (int i = 0; i < clusters; i++) {
            c[i] = BarabasiAlbert.generate(totalVertices, stepEdges, initialClique, a, R);
        }

        Vertex[] randomVertices = new Vertex[clusters];
        for (int i = 0; i < clusters; i++) {
            randomVertices[i] = c[i].getRandomVertex();
        }

        Graph g = Graph.combineGraphs(c);

        for (int i = 0; i < clusters; i++) {
            Vertex s = randomVertices[i];
            Vertex t = randomVertices[(i + 1) % clusters];
            g.addEdge(s, t, true);
        }

        return g.setName("BarabasiAlbertCluster").setMeta(String.format("%s,totalVertices=%d,initialClique=%d,stepEdges=%d,a=%f,clusters=%d", "BarabasiAlbertCluster", totalVertices, initialClique, stepEdges, a, clusters));
    }

    public static Graph generate(int totalVertices, int stepEdges, int initialClique, double a, int clusters) {
        return generate(totalVertices, initialClique, stepEdges, a, clusters, null);
    }
}