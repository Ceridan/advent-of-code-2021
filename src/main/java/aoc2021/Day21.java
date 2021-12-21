package aoc2021;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day21 {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> data = Utils.readInputAsStringArray("day21.txt");

        System.out.printf("Day 21, part 1: %d\n", part1(data));
        System.out.printf("Day 21, part 2: %d\n", part2(data));
    }

    static long part1(List<String> data) {
        Player[] players = initPlayers(data);
        Dice dice = new DeterministicDice();
        Game game = new Game(dice, 1000);
        int playerIndex;

        do {
            playerIndex = game.nextPlayer();
            int value = game.dice.roll() + game.dice.roll() + game.dice.roll();
            players[playerIndex] = game.move(players[playerIndex], value);
        } while (!game.checkEnd(players[playerIndex].score));

        return game.dice.rollsCount * Math.min(players[0].score, players[1].score);
    }

    static long part2(List<String> data) {
        Player[] players = initPlayers(data);
        Dice dice = new DiracDice();
        Game game = new Game(dice, 21);
        HashMap<GameState, LongPair> cache = new HashMap<>();

        LongPair pair = playDiracDiceGame(new GameState(0, players[0], players[1]), game, cache);
        System.out.println(pair.item1 + " " + pair.item2);
        return pair.item1;
    }

    private static LongPair playDiracDiceGame(GameState state, Game game, HashMap<GameState, LongPair> cache) {
        if (cache.containsKey(state)) {
            return cache.get(state);
        }

        LongPair result = new LongPair(0, 0);

        Player activePlayer = state.getActivePlayer();

        for (int i = 0; i < 27; i++) {
            int rollSum = ((DiracDice) game.dice).roll(i);
            Player movedPlayer = game.move(activePlayer, rollSum);

            if (game.checkEnd(movedPlayer.score)) {
                if (state.turn % 2 == 0) {
                    result.item1 += 1;
                } else {
                    result.item2 += 1;
                }
            } else {
                GameState newGameState;

                if (state.turn % 2 == 0) {
                    newGameState = new GameState(state.turn + 1, movedPlayer, state.getPassivePlayer());
                } else {
                    newGameState = new GameState(state.turn + 1, state.getPassivePlayer(), movedPlayer);
                }

                LongPair recursiveResult = playDiracDiceGame(newGameState, game, cache);
                result.item1 += recursiveResult.item1;
                result.item2 += recursiveResult.item2;
            }
        }

        cache.put(state, result);
        return result;
    }

    private static Player[] initPlayers(List<String> data) {
        Player[] players = new Player[2];

        Pattern p = Pattern.compile("Player (\\d) starting position: (\\d+)");

        for (int i = 0; i <= 1; i++) {
            Matcher m = p.matcher(data.get(i));

            if (m.find()) {
                int id = Integer.parseInt(m.group(1));
                int startingPosition = Integer.parseInt(m.group(2));
                players[i] = new Player(startingPosition, 0);
            } else {
                throw new IllegalArgumentException();
            }
        }

        return players;
    }

    private static class Game {
        private final Dice dice;
        private final int endScore;
        private int turn = 0;

        public Game(Dice dice, int endScore) {
            this.dice = dice;
            this.endScore = endScore;
        }

        public int nextPlayer() {
            return turn++ % 2;
        }

        public boolean checkEnd(int score) {
            return score >= endScore;
        }

        public Player move(Player player, int value) {
            int position = (player.position - 1 + value) % 10 + 1;
            int score = player.score + position;
            return new Player(position, score);
        }
    }

    private static class Player {
        private final int position;
        private final int score;

        public Player(int position, int score) {
            this.position = position;
            this.score = score;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Player player = (Player) o;
            return position == player.position && score == player.score;
        }

        @Override
        public int hashCode() {
            return Objects.hash(position, score);
        }
    }

    private static class GameState {
        private final int turn;
        private final Player player1;
        private final Player player2;

        public GameState(int turn, Player player1, Player player2) {
            this.turn = turn;
            this.player1 = player1;
            this.player2 = player2;
        }

        public Player getActivePlayer() {
            return turn % 2 == 0 ? this.player1 : this.player2;
        }

        public Player getPassivePlayer() {
            return turn % 2 == 1 ? this.player1 : this.player2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GameState gameState = (GameState) o;
            return turn == gameState.turn && player1.equals(gameState.player1) && player2.equals(gameState.player2);
        }

        @Override
        public int hashCode() {
            return Objects.hash(turn, player1, player2);
        }
    }

    private static abstract class Dice {
        protected long rollsCount = 0;

        public abstract int roll();
    }

    private static class DeterministicDice extends Dice {
        private int lastValue = 0;

        @Override
        public int roll() {
            rollsCount++;
            lastValue = (lastValue % 100) + 1;
            return lastValue;
        }
    }

    private static class DiracDice extends Dice {
        private final int[] diceSum;

        public DiracDice() {
            diceSum = new int[27];
            init();
        }

        public int roll(int index) {
            return diceSum[index];
        }

        private void init() {
            int d = 0;
            for (int i = 1; i <= 3; i++) {
                for (int j = 1; j <= 3; j++) {
                    for (int k = 1; k <= 3; k++) {
                        diceSum[d++] = i + j + k;
                    }
                }
            }
        }

        @Override
        public int roll() {
            return 0;
        }
    }

    private static class LongPair {
        private long item1;
        private long item2;

        private LongPair(long item1, long item2) {
            this.item1 = item1;
            this.item2 = item2;
        }
    }
}
