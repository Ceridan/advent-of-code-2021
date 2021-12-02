package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day02Tests {

    @Test
    void Part1_Example() {
        List<String> data = Arrays.asList(
            "forward 5",
            "down 5",
            "forward 8",
            "up 3",
            "down 8",
            "forward 2");

        long result = Day02.Part1(data);

        assertEquals(150, result);
    }

    @Test
    void Part2_Example() {
        List<String> data = Arrays.asList(
            "forward 5",
            "down 5",
            "forward 8",
            "up 3",
            "down 8",
            "forward 2");

        long result = Day02.Part2(data);

        assertEquals(900, result);
    }
}


