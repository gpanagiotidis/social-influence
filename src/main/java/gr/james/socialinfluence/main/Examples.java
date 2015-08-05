package gr.james.socialinfluence.main;

import gr.james.socialinfluence.algorithms.generators.RandomGenerator;
import gr.james.socialinfluence.algorithms.scoring.Degree;
import gr.james.socialinfluence.api.GraphState;
import gr.james.socialinfluence.graph.MemoryGraph;
import gr.james.socialinfluence.graph.Vertex;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class Examples {
    public static void main(String[] args) throws IOException {
        int n = 10000;
        double p = 0.2;

        MemoryGraph g = new RandomGenerator<>(MemoryGraph.class, n, p).create();

        // Find largest degree
        int largestDegree = 0;
        GraphState<Integer> degrees = Degree.execute(g, true);
        for (int d : degrees.values()) {
            if (d > largestDegree) {
                largestDegree = d;
            }
        }

        // Get degree distribution
        double[] degreeDistribution = new double[largestDegree];
        for (Map.Entry<Vertex, Integer> e : degrees.entrySet()) {
            degreeDistribution[e.getValue() - 1]++;
        }

        System.out.println(Arrays.toString(degreeDistribution));
    }
}
