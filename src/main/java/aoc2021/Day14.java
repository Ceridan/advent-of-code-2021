package aoc2021;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day14 {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> data = Utils.readInputAsStringArray("day14.txt");

        System.out.printf("Day 14, part 1: %d\n", part1(data));
        System.out.printf("Day 14, part 2: %d\n", part2(data));
    }

    static long part1(List<String> data) {
        return calculatePolymerFormula(data, 10);
    }

    static long part2(List<String> data) {
        return calculatePolymerFormula(data, 40);
    }

    private static long calculatePolymerFormula(List<String> data, int steps) {
        Map<CharPair, Long> template = buildTemplate(data.get(0));
        Map<CharPair, Character> insertions = getInsertions(data);
        Map<Character, Long> charCounts = new HashMap<>();

        for (char ch : data.get(0).toCharArray()) {
            charCounts.merge(ch, 1L, Long::sum);
        }

        for (int step = 1; step <= steps; step++) {
            Map<CharPair, Long> newTemplate = new HashMap<>();

            for (Map.Entry<CharPair, Long> pair : template.entrySet()) {
                Character element = insertions.get(pair.getKey());
                newTemplate.merge(new CharPair(pair.getKey().left, element), pair.getValue(), Long::sum);
                newTemplate.merge(new CharPair(element, pair.getKey().right), pair.getValue(), Long::sum);
                charCounts.merge(element, pair.getValue(), Long::sum);
            }

            template = newTemplate;
        }

        long min = charCounts.values().stream().mapToLong(v -> v).min().orElse(0L);
        long max = charCounts.values().stream().mapToLong(v -> v).max().orElse(0L);

        return max - min;
    }

    private static Map<CharPair, Long> buildTemplate(String templateString) {
        Map<CharPair, Long> template = new HashMap<>();

        for (int i = 0; i < templateString.length() - 1; i++) {
            template.merge(new CharPair(templateString.charAt(i), templateString.charAt(i + 1)), 1L, Long::sum);
        }

        return template;
    }

    private static Map<CharPair, Character> getInsertions(List<String> data) {
        Map<CharPair, Character> insertions = new HashMap<>();

        for (int i = 2; i < data.size(); i++) {
            String[] insertionParts = data.get(i).split(" -> ");
            insertions.put(new CharPair(insertionParts[0]), insertionParts[1].charAt(0));
        }

        return insertions;
    }

    private static class CharPair {
        private final Character left;
        private final Character right;

        private CharPair(String pair) {
            this.left = pair.charAt(0);
            this.right = pair.charAt(1);
        }

        private CharPair(Character left, Character right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CharPair charPair = (CharPair) o;
            return Objects.equals(left, charPair.left) && Objects.equals(right, charPair.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, right);
        }
    }
}
