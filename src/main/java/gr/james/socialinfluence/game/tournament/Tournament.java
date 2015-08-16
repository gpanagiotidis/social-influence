package gr.james.socialinfluence.game.tournament;

import gr.james.socialinfluence.api.GraphGenerator;
import gr.james.socialinfluence.api.GraphImporter;
import gr.james.socialinfluence.game.Game;
import gr.james.socialinfluence.game.GameDefinition;
import gr.james.socialinfluence.game.GameResult;
import gr.james.socialinfluence.game.Player;
import gr.james.socialinfluence.util.Conditions;
import gr.james.socialinfluence.util.Helper;
import gr.james.socialinfluence.util.collections.GenericPair;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Tournament {
    public static final int WIN = 4;
    public static final int LOSE = 1;
    public static final int DRAW = 2;

    private TournamentHandler handler;
    private Set<Player> players = new HashSet<>();
    private Map<GenericPair<GraphGenerator, GameDefinition>, Map<Player, Integer>> scores = new HashMap<>();

    public Tournament(TournamentHandler handler, Player... players) {
        this.handler = handler;
        for (Player p : players) {
            this.players.add(Conditions.requireNonNull(p));
        }
    }

    public Tournament(Player... players) {
        this(null, players);
    }

    public void run(GraphImporter i, InputStream s, GameDefinition d) {
        run(() -> {
            try {
                return i.from(s);
            } catch (IOException e) {
                throw Helper.convertCheckedException(e);
            }
        }, d);
    }

    public String getAllScoresInDsv(String delimiter) {
        Comparator<Player> pComparator = (o1, o2) -> o1.toString().compareTo(o2.toString());
        String csv = String.format("%s%s%s%s%s%n", "GRAPH", delimiter, "DEFINITION", delimiter, players.stream()
                .sorted(pComparator).map(Player::toString).collect(Collectors.joining(delimiter)));
        for (GenericPair<GraphGenerator, GameDefinition> g : scores.keySet()) {
            String hilariousStreamExpression = scores.get(g).keySet().stream().sorted(pComparator)
                    .map(item -> scores.get(g).get(item).toString()).collect(Collectors.joining(delimiter));
            csv += String.format("\"%s\"%s\"%s\"%s%s%n", g.getFirst().create(), delimiter, g.getSecond(), delimiter,
                    hilariousStreamExpression);
        }
        return csv;
    }

    public String getAllScoresInCsv() {
        return getAllScoresInDsv(",");
    }

    public Map<Player, Integer> run(GraphGenerator generator, GameDefinition d) {
        Map<Player, Integer> score = new HashMap<>();
        for (Player p : players) {
            score.put(p, 0);
        }

        int done = 0;
        for (Player a : players) {
            for (Player b : players) {
                if (a != b) {
                    GameResult r = Game.runPlayers(a, b, generator.create(), d);
                    if (r.score < 0) {
                        score.put(a, score.get(a) + WIN);
                        score.put(b, score.get(b) + LOSE);
                    } else if (r.score > 0) {
                        score.put(a, score.get(a) + LOSE);
                        score.put(b, score.get(b) + WIN);
                    } else {
                        score.put(a, score.get(a) + DRAW);
                        score.put(b, score.get(b) + DRAW);
                    }
                    if (handler != null) {
                        handler.progressChanged(++done, players.size() * (players.size() - 1));
                    }
                }
            }
        }

        scores.put(new GenericPair<>(generator, d), score);

        return Collections.unmodifiableMap(score);
    }
}
