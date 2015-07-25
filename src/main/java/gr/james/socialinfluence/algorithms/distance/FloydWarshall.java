package gr.james.socialinfluence.algorithms.distance;

import gr.james.socialinfluence.api.Graph;
import gr.james.socialinfluence.graph.Edge;
import gr.james.socialinfluence.graph.Vertex;
import gr.james.socialinfluence.util.collections.VertexPair;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Implementation of the <a href="http://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm">Floyd-Warshall
 * algorithm</a>. In computer science, the Floyd-Warshall algorithm is an algorithm for finding shortest paths in a
 * weighted graph with positive or negative edge weights (but with no negative cycles). A single execution of the
 * algorithm will find the lengths (summed weights) of the shortest paths between all pairs of vertices, though it does
 * not return details of the paths themselves.</p>
 */
public class FloydWarshall {
    public static Map<VertexPair, Double> execute(Graph g) {
        Map<VertexPair, Double> dist = new HashMap<>();

        for (Vertex u : g.getVertices()) {
            for (Vertex v : g.getVertices()) {
                if (u.equals(v)) {
                    dist.put(new VertexPair(u, v), 0.0);
                } else {
                    dist.put(new VertexPair(u, v), Double.POSITIVE_INFINITY);
                }
            }
        }

        for (Vertex v : g.getVertices()) {
            for (Map.Entry<Vertex, Edge> e : g.getOutEdges(v).entrySet()) {
                dist.put(new VertexPair(v, e.getKey()), e.getValue().getWeight());
            }
        }

        for (Vertex k : g.getVertices()) {
            for (Vertex i : g.getVertices()) {
                for (Vertex j : g.getVertices()) {
                    if (dist.get(new VertexPair(i, j)) > dist.get(new VertexPair(i, k)) + dist.get(new VertexPair(k, j))) {
                        dist.put(new VertexPair(i, j), dist.get(new VertexPair(i, k)) + dist.get(new VertexPair(k, j)));
                    }
                }
            }
        }

        return dist;
    }
}
