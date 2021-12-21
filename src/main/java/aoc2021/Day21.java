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
        Dice dice = new DeterministicDice();
        Game game = buildGame(data, dice, 1000);

        while (!game.checkEnd()) {
            Player player = game.nextPlayer();
            int value = game.dice.roll() + game.dice.roll() + game.dice.roll();
            player.move(value);
        }

        return game.dice.rollsCount * Math.min(game.player1.score, game.player2.score);
    }

    static long part2(List<String> data) {
        return 0;
    }

    private static Game buildGame(List<String> data, Dice dice, int endScore) {
        List<Player> players = new ArrayList<>();

        Pattern p = Pattern.compile("Player (\\d) starting position: (\\d+)");

        for (int i = 0; i <= 1; i++) {
            Matcher m = p.matcher(data.get(i));

            if (m.find()) {
                int id = Integer.parseInt(m.group(1));
                int startingPosition = Integer.parseInt(m.group(2));
                players.add(new Player(id, startingPosition));
            } else {
                throw new IllegalArgumentException();
            }
        }

        return new Game(dice, players.get(0), players.get(1), endScore);
    }

    private static class Game {
        private final Player player1;
        private final Player player2;
        private final Dice dice;
        private final int endScore;
        private int index = -1;

        public Game(Dice dice, Player player1, Player player2, int endScore) {
            this.dice = dice;
            this.player1 = player1;
            this.player2 = player2;
            this.endScore = endScore;
        }

        public Player nextPlayer() {
            index = (index + 1) % 2;
            return index == 0 ? player1 : player2;
        }

        public boolean checkEnd() {
            return player1.score >= endScore || player2.score >= endScore;
        }
    }

    private static class Player {
        private final int id;
        private int position;
        private int score = 0;

        public Player(int id, int startingPosition) {
            this.id = id;
            position = startingPosition;
        }

        private void move(int value) {
            position = (position - 1 + value) % 10 + 1;
            score += position;
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
}
