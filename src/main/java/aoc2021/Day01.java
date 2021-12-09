package aoc2021;

import java.io.FileNotFoundException;
import java.util.List;

public class Day01 {

    public static void main(String[] args) throws FileNotFoundException {
        List<Integer> data = Utils.readInputAsIntegerArray("day01.txt");

        System.out.printf("Day 01, part 1: %d\n", part1(data));
        System.out.printf("Day 01, part 2: %d\n", part2(data));
    }

    static int part1(List<Integer> data) {
        int increases = 0;

        for (int i = 1; i < data.size(); i++) {
            if (data.get(i) > data.get(i - 1)) {
                increases++;
            }
        }

        return increases;
    }

    static int part2(List<Integer> data) {
        int increases = 0;

        for (int i = 3; i < data.size(); i++) {
            if (data.get(i) > data.get(i - 3)) {
                increases++;
            }
        }

        return increases;
    }
}
