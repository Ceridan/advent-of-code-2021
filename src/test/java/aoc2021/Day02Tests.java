package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day02Tests {

    @Test
    void part1_example() {
        List<String> data = List.of(
            "forward 5",
            "down 5",
            "forward 8",
            "up 3",
            "down 8",
            "forward 2");

        long result = Day02.part1(data);

        assertEquals(150, result);
    }

    @Test
    void part2_example() {
        List<String> data = List.of(
            "forward 5",
            "down 5",
            "forward 8",
            "up 3",
            "down 8",
            "forward 2");

        long result = Day02.part2(data);

        assertEquals(900, result);
    }
}
