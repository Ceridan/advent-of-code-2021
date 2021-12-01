package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01Tests {

    @Test
    void Part1_Example() {
        List<Integer> data = Arrays.asList(199, 200, 208, 210, 200, 207, 240, 269, 260, 263);

        int result = Day01.Part1(data);

        assertEquals(7, result);
    }

    @Test
    void Part2_Example() {
        List<Integer> data = Arrays.asList(199, 200, 208, 210, 200, 207, 240, 269, 260, 263);

        int result = Day01.Part2(data);

        assertEquals(5, result);
    }
}


