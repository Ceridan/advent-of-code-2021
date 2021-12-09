package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day09Tests {
    private static final List<String> data = List.of(
        "2199943210",
        "3987894921",
        "9856789892",
        "8767896789",
        "9899965678");


    @Test
    void part1_example() {
        int result = Day09.part1(data);

        assertEquals(15, result);
    }

    @Test
    void part2_example() {
        int result = Day09.part2(data);

        assertEquals(1134, result);
    }
}


