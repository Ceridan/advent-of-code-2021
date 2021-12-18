package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day17Tests {
    @Test
    void part1_example() {
        String data = "target area: x=20..30, y=-10..-5";

        int result = Day17.part1(data);

        assertEquals(45, result);
    }

    @Test
    void part2_example() {
        String data = "target area: x=20..30, y=-10..-5";

        int result = Day17.part2(data);

        assertEquals(112, result);
    }
}
