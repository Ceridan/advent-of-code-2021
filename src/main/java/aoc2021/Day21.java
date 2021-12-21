package aoc2021;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
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
        DeterministicDice dice = new DeterministicDice();
        GameState state = new GameState(0, players[0], players[1]);
        Player activePlayer;

        do {
            int rollSum = dice.roll() + dice.roll() + dice.roll();
            activePlayer = move(state.getActivePlayer(), rollSum);
            state = getStateForNextTurn(state, activePlayer);
        } while (activePlayer.score < 1000);

        return (long) dice.rollsCount * Math.min(state.player1.score, state.player2.score);
    }

    static long part2(List<String> data) {
        Player[] players = initPlayers(data);
        DiracDice dice = new DiracDice();
        HashMap<GameState, Long> cache = new HashMap<>();

        return playDiracDiceGame(dice, new GameState(0, players[0], players[1]), cache, 21);
    }

    private static Player move(Player player, int value) {
        int position = (player.position - 1 + value) % 10 + 1;
        int score = player.score + position;
        return new Player(position, score);
    }

    private static GameState getStateForNextTurn(GameState currentState, Player activePlayer) {
        GameState nextState;

        if (currentState.turn % 2 == 0) {
            nextState = new GameState(currentState.turn + 1, activePlayer, currentState.getPassivePlayer());
        } else {
            nextState = new GameState(currentState.turn + 1, currentState.getPassivePlayer(), activePlayer);
        }

        return nextState;
    }

    private static long playDiracDiceGame(DiracDice dice, GameState state, HashMap<GameState, Long> cache, int endScore) {
        if (cache.containsKey(state)) {
            return cache.get(state);
        }

        long result = 0;

        for (int i = 0; i < DiracDice.dimensions; i++) {
            int rollSum = dice.tripleRoll(i);
            Player activePlayer = move(state.getActivePlayer(), rollSum);

            if (activePlayer.score >= endScore) {
                if (state.turn % 2 == 0) {
                    result++;
                }
            } else {
                GameState newGameState = getStateForNextTurn(state, activePlayer);

                result += playDiracDiceGame(dice, newGameState, cache, endScore);
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
                int startingPosition = Integer.parseInt(m.group(2));
                players[i] = new Player(startingPosition, 0);
            } else {
                throw new IllegalArgumentException();
            }
        }

        return players;
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

    private static class DeterministicDice {
        private int rollsCount = 0;
        private int lastValue = 0;

        public int roll() {
            rollsCount++;
            lastValue = (lastValue % 100) + 1;
            return lastValue;
        }
    }

    private static class DiracDice {
        public static final int dimensions = 27;
        private final int[] diceSum;

        public DiracDice() {
            diceSum = new int[dimensions];
            init();
        }

        public int tripleRoll(int dimensionIndex) {
            return diceSum[dimensionIndex];
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
    }
}
