package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day23Tests {
    @Test
    void part1_example() {
        List<String> data = List.of(
            "#############",
            "#...........#",
            "###B#C#B#D###",
            "  #A#D#C#A#",
            "  #########");

        int result = Day23.part1(data);

        assertEquals(12521, result);
    }

//    @Test
//    void part2_example() {
//        List<String> data = List.of(
//            "#############",
//            "#...........#",
//            "###B#C#B#D###",
//            "  #D#C#B#A#",
//            "  #D#B#A#C#",
//            "  #A#D#C#A#",
//            "  #########");
//
//        int result = Day23.part2(data);
//
//        assertEquals(44169, result);
//    }
}
