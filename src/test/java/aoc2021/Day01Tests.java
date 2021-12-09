package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01Tests {

    @Test
    void part1_example() {
        List<Integer> data = List.of(199, 200, 208, 210, 200, 207, 240, 269, 260, 263);

        int result = Day01.part1(data);

        assertEquals(7, result);
    }

    @Test
    void part2_example() {
        List<Integer> data = List.of(199, 200, 208, 210, 200, 207, 240, 269, 260, 263);

        int result = Day01.part2(data);

        assertEquals(5, result);
    }
}
