package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day16Tests {
    @Test
    void part1_example_6() {
        String data = "D2FE28";

        int result = Day16.part1(data);

        assertEquals(6, result);
    }

    @Test
    void part1_example_16() {
        String data = "8A004A801A8002F478";

        int result = Day16.part1(data);

        assertEquals(16, result);
    }

    @Test
    void part1_example_12() {
        String data = "620080001611562C8802118E34";

        int result = Day16.part1(data);

        assertEquals(12, result);
    }

    @Test
    void part1_example_23() {
        String data = "C0015000016115A2E0802F182340";

        int result = Day16.part1(data);

        assertEquals(23, result);
    }

    @Test
    void part1_example_31() {
        String data = "A0016C880162017C3686B18A3D4780";

        int result = Day16.part1(data);

        assertEquals(31, result);
    }

    @Test
    void part2_example_sum() {
        String data = "C200B40A82";

        long result = Day16.part2(data);

        assertEquals(3, result);
    }

    @Test
    void part2_example_product() {
        String data = "04005AC33890";

        long result = Day16.part2(data);

        assertEquals(54, result);
    }

    @Test
    void part2_example_minimum() {
        String data = "880086C3E88112";

        long result = Day16.part2(data);

        assertEquals(7, result);
    }

    @Test
    void part2_example_maximum() {
        String data = "CE00C43D881120";

        long result = Day16.part2(data);

        assertEquals(9, result);
    }

    @Test
    void part2_example_less() {
        String data = "D8005AC2A8F0";

        long result = Day16.part2(data);

        assertEquals(1, result);
    }

    @Test
    void part2_example_not_greater() {
        String data = "F600BC2D8F";

        long result = Day16.part2(data);

        assertEquals(0, result);
    }

    @Test
    void part2_example_not_equals() {
        String data = "9C005AC2F8F0";

        long result = Day16.part2(data);

        assertEquals(0, result);
    }

    @Test
    void part2_example_equals() {
        String data = "9C0141080250320F1802104A08";

        long result = Day16.part2(data);

        assertEquals(1, result);
    }
}
