package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day03Tests {

    @Test
    void part1_example() {
        List<String> data = List.of(
            "00100",
            "11110",
            "10110",
            "10111",
            "10101",
            "01111",
            "00111",
            "11100",
            "10000",
            "11001",
            "00010",
            "01010");

        int result = Day03.part1(data);

        assertEquals(198, result);
    }

    @Test
    void part2_example() {
        List<String> data = List.of(
            "00100",
            "11110",
            "10110",
            "10111",
            "10101",
            "01111",
            "00111",
            "11100",
            "10000",
            "11001",
            "00010",
            "01010");

        int result = Day03.part2(data);

        assertEquals(230, result);
    }
}
