package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day24Tests {

    @Test
    void part1_ALU_invert() {
        List<String> program = List.of(
            "inp x",
            "mul x -1");

        Day24.ALU alu = new Day24.ALU();
        alu.run(program, 10);

        assertEquals(-10, alu.getX());
    }

    @Test
    void part1_ALU_triple_equal() {
        List<String> program = List.of(
            "inp z",
            "inp x",
            "mul z 3",
            "eql z x");

        Day24.ALU alu = new Day24.ALU();
        alu.run(program, 10, 30);

        assertEquals(1, alu.getZ());
    }

    @Test
    void part1_ALU_binary_number() {
        List<String> program = List.of(
            "inp w",
            "add z w",
            "mod z 2",
            "div w 2",
            "add y w",
            "mod y 2",
            "div w 2",
            "add x w",
            "mod x 2",
            "div w 2",
            "mod w 2");

        Day24.ALU alu = new Day24.ALU();
        alu.run(program, 11);

        assertEquals(1, alu.getW());
        assertEquals(0, alu.getX());
        assertEquals(1, alu.getY());
        assertEquals(1, alu.getZ());
    }
}
