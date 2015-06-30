package gr.james.socialinfluence.game.players;

import gr.james.socialinfluence.game.Move;
import gr.james.socialinfluence.graph.Vertex;
import gr.james.socialinfluence.graph.algorithms.Dijkstra;
import gr.james.socialinfluence.graph.algorithms.iterators.PageRankIterator;

import java.util.HashMap;
import java.util.Map;

public class GreedyPlayer extends Player {
    public static Move getMinimum(HashMap<Move, Double> treeMoves) {
        Double minSumMove = Double.POSITIVE_INFINITY;
        Move minMove = null;
        for (Map.Entry<Move, Double> e : treeMoves.entrySet()) {
            if (e.getValue() < minSumMove) {
                minSumMove = e.getValue();
                minMove = e.getKey();
            }
        }
        return minMove;
    }

    public static void updateVector(HashMap<Vertex[], Double> distanceMap, HashMap<Vertex, Double> vector, Vertex move) {
        for (Map.Entry<Vertex[], Double> e : distanceMap.entrySet()) {
            Vertex[] tt = e.getKey();
            if (tt[0].equals(move)) {
                vector.put(tt[1], vector.get(tt[1]) * (1 - e.getValue()));
            }
        }
    }

    public static Double vectorSum(HashMap<Vertex, Double> v) {
        Double sum = 0.0;
        for (Map.Entry<Vertex, Double> e : v.entrySet()) {
            sum += e.getValue();
        }
        return sum;
    }

    @Override
    public void getMove() {
        /* Here be distanceMap and vector */
        HashMap<Vertex[], Double> distanceMap = new HashMap<Vertex[], Double>();
        HashMap<Vertex, Double> vector = new HashMap<Vertex, Double>();

        /* Fill the distanceMap */
        // TODO: Replace this snippet with FloydWarshall method, but care, this map has (t,s) rather than (s,t)
        for (Vertex v : this.g.getVertices()) {
            HashMap<Vertex, Double> temp = Dijkstra.execute(this.g, v);
            for (Map.Entry<Vertex, Double> e : temp.entrySet()) {
                distanceMap.put(new Vertex[]{e.getKey(), v}, e.getValue());
            }
        }
        // TODO: Replace up to here
        for (Map.Entry<Vertex[], Double> e : distanceMap.entrySet()) {
            e.setValue(1 / Math.exp(e.getValue()));
        }

        HashMap<Move, Double> treeMoves = new HashMap<Move, Double>();

        PageRankIterator pri = new PageRankIterator(this.g, 0.0);
        while (pri.hasNext() && !this.isInterrupted()) {
            Vertex firstGuess = pri.next();

            /* Initialize the vector */
            vector.clear();
            for (Vertex v : this.g.getVertices()) {
                vector.put(v, 1.0);
            }

            /* Insert the first node to the move and update the vector */
            Move m = new Move();
            m.putVertex(firstGuess, 1.0);
            updateVector(distanceMap, vector, firstGuess);

            /* Simulation loop */
            while (m.getVerticesCount() < d.getActions()) {
                HashMap<Vertex, Double> sumMap = new HashMap<Vertex, Double>();
                for (Vertex v : this.g.getVertices()) {
                    HashMap<Vertex, Double> tmpVector = new HashMap<Vertex, Double>();
                    for (Map.Entry<Vertex, Double> e : vector.entrySet()) {
                        tmpVector.put(e.getKey(), e.getValue());
                    }
                    updateVector(distanceMap, tmpVector, v);
                    sumMap.put(v, vectorSum(tmpVector));
                }
                Double minSum = Double.POSITIVE_INFINITY;
                Vertex minNode = null;
                for (Map.Entry<Vertex, Double> e : sumMap.entrySet()) {
                    if (e.getValue() < minSum) {
                        minNode = e.getKey();
                        minSum = e.getValue();
                    }
                }
                m.putVertex(minNode, 1.0);
                updateVector(distanceMap, vector, minNode);
            }

            /* Insert this move to the list */
            m.normalizeWeights(d.getBudget()); // TODO: Can this line safely be removed?
            treeMoves.put(m, vectorSum(vector));

            /* Find the best move, aka the one with min vector sum */
            Move minMove = getMinimum(treeMoves);
            this.movePtr.submit(minMove);

            /* This helps when computation takes a long time */
            if (!this.d.getTournament()) {
                log.info("{} : {}", firstGuess, minMove);
            }
        }
    }
}