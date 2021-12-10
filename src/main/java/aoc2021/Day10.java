package aoc2021;

import java.io.FileNotFoundException;
import java.util.*;

public class Day10 {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> data = Utils.readInputAsStringArray("day10.txt");

        System.out.printf("Day 10, part 1: %d\n", part1(data));
        System.out.printf("Day 10, part 2: %d\n", part2(data));
    }

    static long part1(List<String> data) {
        long totalCorruptedScore = 0;

        for (String line : data) {
            totalCorruptedScore += calculateCorruptedScore(line);
        }

        return totalCorruptedScore;
    }

    static long part2(List<String> data) {
        ArrayList<Long> incompleteScores = new ArrayList<>();

        for (String line : data) {
            long incompleteScore = calculateIncompleteScore(line);

            if (incompleteScore > 0) {
                incompleteScores.add(incompleteScore);
            }
        }

        incompleteScores.sort(Comparator.naturalOrder());
        return incompleteScores.get(incompleteScores.size() / 2);
    }

    private static long calculateCorruptedScore(String line) {
        Map<Character, Character> closeToOpen = new HashMap<>();
        closeToOpen.put(')', '(');
        closeToOpen.put(']', '[');
        closeToOpen.put('}', '{');
        closeToOpen.put('>', '<');

        Map<Character, Integer> parenthesisScores = new HashMap<>();
        parenthesisScores.put(')', 3);
        parenthesisScores.put(']', 57);
        parenthesisScores.put('}', 1197);
        parenthesisScores.put('>', 25137);

        Stack<Character> stack = new Stack<>();

        for (char ch : line.toCharArray()) {
            if (!closeToOpen.containsKey(ch)) {
                stack.add(ch);
                continue;
            }

            if (stack.isEmpty() || closeToOpen.get(ch) != stack.pop()) {
                return parenthesisScores.get(ch);
            }
        }

        return 0;
    }

    private static long calculateIncompleteScore(String line) {
        Map<Character, Character> closeToOpen = new HashMap<>();
        closeToOpen.put(')', '(');
        closeToOpen.put(']', '[');
        closeToOpen.put('}', '{');
        closeToOpen.put('>', '<');

        Map<Character, Integer> parenthesisScores = new HashMap<>();
        parenthesisScores.put('(', 1);
        parenthesisScores.put('[', 2);
        parenthesisScores.put('{', 3);
        parenthesisScores.put('<', 4);

        Stack<Character> stack = new Stack<>();

        for (char ch : line.toCharArray()) {
            if (!closeToOpen.containsKey(ch)) {
                stack.add(ch);
                continue;
            }

            if (stack.isEmpty() || closeToOpen.get(ch) != stack.pop()) {
                return 0;
            }
        }

        long incompleteScore = 0;

        while (!stack.isEmpty()) {
            char ch = stack.pop();
            incompleteScore = incompleteScore * 5 + parenthesisScores.get(ch);
        }

        return incompleteScore;
    }
}
