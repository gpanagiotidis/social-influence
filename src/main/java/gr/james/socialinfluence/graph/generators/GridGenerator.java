package gr.james.socialinfluence.graph.generators;

import gr.james.socialinfluence.api.Graph;
import gr.james.socialinfluence.api.GraphGenerator;
import gr.james.socialinfluence.graph.Vertex;
import gr.james.socialinfluence.util.Helper;

import java.util.Set;

/**
 * <p>Generates a two-dimensional, undirected, n x m grid graph.</p>
 *
 * @see <a href="http://mathworld.wolfram.com/GridGraph.html">http://mathworld.wolfram.com/GridGraph.html</a>
 */
public class GridGenerator<T extends Graph> implements GraphGenerator<T> {
    private Class<T> type;
    private int n, m;

    public GridGenerator(Class<T> type, int n, int m) {
        this.type = type;
        this.n = n;
        this.m = m;
    }

    @Override
    public T create() {
        T g = Helper.instantiateGeneric(type);

        int count = 0;
        Set<Vertex> set = g.addVertices(n * m);
        Vertex[] a = set.toArray(new Vertex[n * m]);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (j != m - 1) {
                    g.addEdge(a[count], a[count + 1], true);
                }
                if (i != n - 1) {
                    g.addEdge(a[count], a[count + m], true);
                }

                count = count + 1;
            }
        }

        g.setMeta("type", "Grid")
                .setMeta("n", String.valueOf(n))
                .setMeta("m", String.valueOf(m));

        return g;
    }
}
