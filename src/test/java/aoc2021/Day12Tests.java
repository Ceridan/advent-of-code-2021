package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12Tests {
    @Test
    void part1_example_small() {
        List<String> data = List.of(
            "start-A",
            "start-b",
            "A-c",
            "A-b",
            "b-d",
            "A-end",
            "b-end");

        int result = Day12.part1(data);

        assertEquals(10, result);
    }

    @Test
    void part1_example_medium() {
        List<String> data = List.of(
            "dc-end",
            "HN-start",
            "start-kj",
            "dc-start",
            "dc-HN",
            "LN-dc",
            "HN-end",
            "kj-sa",
            "kj-HN",
            "kj-dc");

        int result = Day12.part1(data);

        assertEquals(19, result);
    }

    @Test
    void part1_example_large() {
        List<String> data = List.of(
            "fs-end",
            "he-DX",
            "fs-he",
            "start-DX",
            "pj-DX",
            "end-zg",
            "zg-sl",
            "zg-pj",
            "pj-he",
            "RW-he",
            "fs-DX",
            "pj-RW",
            "zg-RW",
            "start-pj",
            "he-WI",
            "zg-he",
            "pj-fs",
            "start-RW");

        int result = Day12.part1(data);

        assertEquals(226, result);
    }

    @Test
    void part2_example_small() {
        List<String> data = List.of(
            "start-A",
            "start-b",
            "A-c",
            "A-b",
            "b-d",
            "A-end",
            "b-end");

        int result = Day12.part2(data);

        assertEquals(36, result);
    }

    @Test
    void part2_example_medium() {
        List<String> data = List.of(
            "dc-end",
            "HN-start",
            "start-kj",
            "dc-start",
            "dc-HN",
            "LN-dc",
            "HN-end",
            "kj-sa",
            "kj-HN",
            "kj-dc");

        int result = Day12.part2(data);

        assertEquals(103, result);
    }

    @Test
    void part2_example_large() {
        List<String> data = List.of(
            "fs-end",
            "he-DX",
            "fs-he",
            "start-DX",
            "pj-DX",
            "end-zg",
            "zg-sl",
            "zg-pj",
            "pj-he",
            "RW-he",
            "fs-DX",
            "pj-RW",
            "zg-RW",
            "start-pj",
            "he-WI",
            "zg-he",
            "pj-fs",
            "start-RW");

        int result = Day12.part2(data);

        assertEquals(3509, result);
    }
}
