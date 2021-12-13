package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13Tests {
    private static final List<String> data = List.of(
        "6,10",
        "0,14",
        "9,10",
        "0,3",
        "10,4",
        "4,11",
        "6,0",
        "6,12",
        "4,1",
        "0,13",
        "10,12",
        "3,4",
        "3,0",
        "8,4",
        "1,10",
        "2,14",
        "8,10",
        "9,0",
        "",
        "fold along y=7",
        "fold along x=5");


    @Test
    void part1_example() {
        int result = Day13.part1(data);

        assertEquals(17, result);
    }

    @Test
    void part2_example() {
        String result = Day13.part2(data);

        assertEquals("\n" +
            "#####\n" +
            "#   #\n" +
            "#   #\n" +
            "#   #\n" +
            "#####\n" +
            "\n", result);
    }
}
