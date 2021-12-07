package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day07Tests {
    @Test
    void part1_example() {
        List<Integer> positions = List.of(16, 1, 2, 0, 4, 2, 7, 1, 2, 14);

        int result = Day07.part1(positions);

        assertEquals(37, result);
    }

    @Test
    void part2_example() {
        List<Integer> positions = List.of(16, 1, 2, 0, 4, 2, 7, 1, 2, 14);

        int result = Day07.part2(positions);

        assertEquals(168, result);
    }
}


