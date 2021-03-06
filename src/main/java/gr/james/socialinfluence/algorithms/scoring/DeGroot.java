package gr.james.socialinfluence.algorithms.scoring;

import gr.james.socialinfluence.api.Graph;
import gr.james.socialinfluence.api.GraphState;
import gr.james.socialinfluence.graph.Edge;
import gr.james.socialinfluence.graph.Vertex;
import gr.james.socialinfluence.util.Conditions;
import gr.james.socialinfluence.util.Finals;
import gr.james.socialinfluence.util.Helper;
import gr.james.socialinfluence.util.collections.EvictingLinkedHashSet;
import gr.james.socialinfluence.util.states.DoubleGraphState;

import java.util.Map;

public class DeGroot {
    public static final int DEFAULT_HISTORY = Integer.MAX_VALUE;

    public static GraphState<Double> execute(Graph g, GraphState<Double> initialOpinions, double epsilon, int history) {
        Conditions.requireArgument(history >= 1, "DeGroot history must be >= 1");

        EvictingLinkedHashSet<GraphState<Double>> stateHistory = new EvictingLinkedHashSet<>(history);

        GraphState<Double> lastState = initialOpinions;
        stateHistory.add(initialOpinions);

        boolean stabilized = false;
        while (!stabilized) {
            GraphState<Double> nextState = new DoubleGraphState(g, 0.0);

            for (Vertex v : g) {
                double vNewValue = 0.0;
                for (Map.Entry<Vertex, Edge> e : g.getOutEdges(v).entrySet()) {
                    vNewValue = vNewValue + (
                            e.getValue().getWeight() * lastState.get(e.getKey())
                    );
                }
                nextState.put(v, vNewValue / Helper.getWeightSum(g.getOutEdges(v).values()));
            }

            if (nextState.subtract(lastState).abs().lessThan(epsilon)) {
                stabilized = true;
            }

            if (!stateHistory.add(nextState)) {
                stabilized = true;
                if (Finals.LOG.isDebugEnabled() && !nextState.equals(lastState)) {
                    Finals.LOG.debug(Finals.L_DEGROOT_PERIODIC, g);
                }
            }

            lastState = nextState;
        }

        return lastState;
    }

    public static GraphState<Double> execute(Graph g, GraphState<Double> initialOpinions, double epsilon) {
        return execute(g, initialOpinions, epsilon, DEFAULT_HISTORY);
    }

    public static GraphState<Double> execute(Graph g, GraphState<Double> initialOpinions, int history) {
        return execute(g, initialOpinions, Finals.DEFAULT_DEGROOT_PRECISION, history);
    }

    public static GraphState<Double> execute(Graph g, GraphState<Double> initialOpinions) {
        return execute(g, initialOpinions, Finals.DEFAULT_DEGROOT_PRECISION, DEFAULT_HISTORY);
    }
}
