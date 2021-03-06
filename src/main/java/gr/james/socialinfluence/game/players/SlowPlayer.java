package gr.james.socialinfluence.game.players;

import gr.james.socialinfluence.algorithms.iterators.RandomVertexIterator;
import gr.james.socialinfluence.api.Graph;
import gr.james.socialinfluence.game.GameDefinition;
import gr.james.socialinfluence.game.Move;
import gr.james.socialinfluence.game.MovePointer;
import gr.james.socialinfluence.game.Player;

public class SlowPlayer extends Player {
    @Override
    public void suggestMove(Graph g, GameDefinition d, MovePointer movePtr) {
        long now = System.currentTimeMillis();

        while (System.currentTimeMillis() - now < 5 * d.getExecution()) {
            Move m = new Move();
            RandomVertexIterator rvi = new RandomVertexIterator(g);
            while (m.getVerticesCount() < d.getActions()) {
                m.putVertex(rvi.next(), 1.0);
            }
            movePtr.submit(m);
        }
    }
}
