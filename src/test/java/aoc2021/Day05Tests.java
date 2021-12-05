package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day05Tests {
    private static final List<String> data = List.of(
        "0,9 -> 5,9",
        "8,0 -> 0,8",
        "9,4 -> 3,4",
        "2,2 -> 2,1",
        "7,0 -> 7,4",
        "6,4 -> 2,0",
        "0,9 -> 2,9",
        "3,4 -> 1,4",
        "0,0 -> 8,8",
        "5,5 -> 8,2");

    @Test
    void part1_example() {
        int result = Day05.part1(data);

        assertEquals(5, result);
    }

    @Test
    void part2_example() {
        int result = Day05.part2(data);

        assertEquals(12, result);
    }
}


