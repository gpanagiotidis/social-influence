package gr.james.socialinfluence.main;

import gr.james.socialinfluence.algorithms.generators.CycleGenerator;
import gr.james.socialinfluence.api.Graph;
import gr.james.socialinfluence.game.GameDefinition;
import gr.james.socialinfluence.game.Player;
import gr.james.socialinfluence.game.players.BruteForcePlayer;
import gr.james.socialinfluence.graph.MemoryGraph;
import gr.james.socialinfluence.graph.Vertex;
import gr.james.socialinfluence.graph.io.Dot;
import gr.james.socialinfluence.util.RandomHelper;

import java.io.IOException;
import java.util.Set;

public class Examples {
    public static void main(String[] args) throws IOException {
        Graph g = new MemoryGraph();
        int n = 50;
        int k = 4;
        double p = 0.1;

        g.addVertices(n);
        Vertex sub = g.getRandomVertex();

        System.out.println(g.getVerticesCount());

        for (int i=0; i<n; i++){
            for (int j=1; j<=k/2; j++){
                if (i+j<n) {
                    g.addEdge(g.getVertexFromIndex(i), g.getVertexFromIndex(i + j), true);
                }
                else{
                    g.addEdge(g.getVertexFromIndex(i), g.getVertexFromIndex(i + j - n), true);
                }
            }
        }

        for (int i=0; i<n; i++){
            for (int j=1; j<=k/2; j++){
                if (i+j<n) {
                    if (p >= RandomHelper.getRandom().nextDouble()) {
                        g.removeEdge(g.getVertexFromIndex(i), g.getVertexFromIndex(i + j));
                        g.removeEdge(g.getVertexFromIndex(i + j), g.getVertexFromIndex(i));
                        sub = g.getRandomVertex();
                        while (sub == g.getVertexFromIndex(i) || sub == g.getVertexFromIndex(i + j) || g.containsEdge(g.getVertexFromIndex(i), sub) || g.containsEdge(sub, g.getVertexFromIndex(i))) {
                            sub = g.getRandomVertex();
                        }
                        g.addEdge(g.getVertexFromIndex(i), sub, true);
                    }
                }
                else {
                    if(p>=RandomHelper.getRandom().nextDouble()){
                        g.removeEdge(g.getVertexFromIndex(i), g.getVertexFromIndex(i + j - n));
                        g.removeEdge(g.getVertexFromIndex(i + j - n), g.getVertexFromIndex(i));
                        sub=g.getRandomVertex();
                        while (sub==g.getVertexFromIndex(i) || sub==g.getVertexFromIndex(i + j - n) || g.containsEdge(g.getVertexFromIndex(i), sub) || g.containsEdge(sub, g.getVertexFromIndex(i))){
                            sub=g.getRandomVertex();
                        }
                        g.addEdge(g.getVertexFromIndex(i), sub, true);
                    }
                }
            }
        }


        System.out.println(g.getEdgesCount());

        Dot dot = new Dot();
            dot.to(g, System.out);
    }
}
