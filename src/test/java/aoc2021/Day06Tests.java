package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day06Tests {
    @Test
    void part1_example_for_18_days() {
        List<Integer> state = List.of(3, 4, 3, 1, 2);

        long result = Day06.calculateSpawns(state, 18);

        assertEquals(26L, result);
    }

    @Test
    void part1_example_for_80_days() {
        List<Integer> state = List.of(3, 4, 3, 1, 2);

        long result = Day06.calculateSpawns(state, 80);

        assertEquals(5934L, result);
    }

    @Test
    void part2_example_for_256_days() {
        List<Integer> state = List.of(3, 4, 3, 1, 2);

        long result = Day06.calculateSpawns(state, 256);

        assertEquals(26984457539L, result);
    }
}


