package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day21Tests {
    @Test
    void part1_example() {
        List<String> data = List.of(
            "Player 1 starting position: 4",
            "Player 2 starting position: 8");

        long result = Day21.part1(data);

        assertEquals(739785L, result);
    }

    @Test
    void part2_example() {
        List<String> data = List.of(
            "Player 1 starting position: 4",
            "Player 2 starting position: 8");
        long result = Day21.part2(data);

        assertEquals(444356092776315L, result);
    }
}
