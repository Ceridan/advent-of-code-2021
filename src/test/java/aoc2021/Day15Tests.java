package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day15Tests {
    private static final List<String> data = List.of(
        "1163751742",
        "1381373672",
        "2136511328",
        "3694931569",
        "7463417111",
        "1319128137",
        "1359912421",
        "3125421639",
        "1293138521",
        "2311944581");

    @Test
    void part1_example() {
        int result = Day15.part1(data);

        assertEquals(40, result);
    }

    @Test
    void part2_example() {
        int result = Day15.part2(data);

        assertEquals(315, result);
    }
}
