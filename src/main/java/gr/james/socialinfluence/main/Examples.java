package gr.james.socialinfluence.main;

import gr.james.socialinfluence.algorithms.generators.RandomGenerator;
import gr.james.socialinfluence.graph.MemoryGraph;
import gr.james.socialinfluence.graph.Vertex;

import java.io.IOException;

public class Examples {
    public static void main(String[] args) throws IOException {
        int n = 1000;
        double p = 0.05;

        MemoryGraph g = new RandomGenerator<>(MemoryGraph.class, n, p).create();

        Vertex v = new Vertex();
        g.getInEdges(v);
    }
}
