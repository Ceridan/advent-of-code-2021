package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day23Tests {
    List<String> data = List.of(
        "#############",
        "#...........#",
        "###B#C#B#D###",
        "  #A#D#C#A#",
        "  #########");

    @Test
    void part1_example() {
        int result = Day23.part1(data);

        assertEquals(12521, result);
    }

    @Test
    void part2_example() {
        int result = Day23.part2(data);

        assertEquals(44169, result);
    }
}
