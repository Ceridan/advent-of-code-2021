package aoc2021;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

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
        List<Character> template = data.get(0).chars().mapToObj(e -> (char) e).collect(Collectors.toList());
        Map<String, Character> insertions = getInsertions(data);
        Map<Character, Long> charCounts = insertions.values().stream().collect(Collectors.toMap(ch -> ch, ch -> 0L, (existing, replacement) -> existing));

        for (Character ch : template) {
            charCounts.merge(ch, 1L, Long::sum);
        }

        long b = 1;

        for (int step = 1; step <= steps; step++) {
            System.out.println(charCounts);
            System.out.println(charCounts.get('B') - b);
            b = charCounts.get('B');
            List<Character> newTemplate = new ArrayList<>(template.size() * 2 - 1);

            for (int i = 0; i < template.size() - 1; i++) {
                Character left = template.get(i);
                Character right = template.get(i + 1);
                Character middle = insertions.get("" + left + right);

                newTemplate.add(left);

                if (middle != null) {
                    newTemplate.add(middle);
                    charCounts.merge(middle, 1L, Long::sum);
                }
            }

            newTemplate.add(template.get(template.size() - 1));

            template = newTemplate;
        }
        System.out.println(charCounts);
        System.out.println(charCounts.get('B') - b);
        long min = charCounts.values().stream().mapToLong(v -> v).min().orElse(0L);
        long max = charCounts.values().stream().mapToLong(v -> v).max().orElse(0L);

        return max - min;
    }

    private static Map<String, Character> getInsertions(List<String> data) {
        Map<String, Character> insertions = new HashMap<>();

        for (int i = 2; i < data.size(); i++) {
            String[] insertionParts = data.get(i).split(" -> ");
            insertions.put(insertionParts[0], insertionParts[1].charAt(0));
        }

        return insertions;
    }
}
