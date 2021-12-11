package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11Tests {
    private static final List<String> data = List.of(
        "5483143223",
        "2745854711",
        "5264556173",
        "6141336146",
        "6357385478",
        "4167524645",
        "2176841721",
        "6882881134",
        "4846848554",
        "5283751526");


    @Test
    void part1_example_10_steps() {
        int result = Day11.part1(data, 10);

        assertEquals(204, result);
    }

    @Test
    void part1_example_100_steps() {
        int result = Day11.part1(data, 100);

        assertEquals(1656, result);
    }

    @Test
    void part2_example() {
        int result = Day11.part2(data);

        assertEquals(195, result);
    }
}
