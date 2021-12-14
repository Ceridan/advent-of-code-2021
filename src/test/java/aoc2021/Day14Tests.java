package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day14Tests {
    private static final List<String> data = List.of(
        "NNCB",
        "",
        "CH -> B",
        "HH -> N",
        "CB -> H",
        "NH -> C",
        "HB -> C",
        "HC -> B",
        "HN -> C",
        "NN -> C",
        "BH -> H",
        "NC -> B",
        "NB -> B",
        "BN -> B",
        "BB -> N",
        "BC -> B",
        "CC -> N",
        "CN -> C");

    @Test
    void part1_example() {
        long result = Day14.part1(data);

        assertEquals(1588, result);
    }

    @Test
    void part2_example() {
        long result = Day14.part2(data);

        assertEquals(2188189693529L, result);
    }
}
