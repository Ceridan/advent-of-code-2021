package aoc2021;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Day03 {
    public static void main(String[] args) throws FileNotFoundException {
        List<String> data = Utils.readInputAsStringArray("day03.txt");

        System.out.printf("Day 03, part 1: %d\n", part1(data));
        System.out.printf("Day 03, part 2: %d\n", part2(data));
    }

    static int part1(List<String> data) {
        List<Integer> numbers = getNumbers(data);
        int size = data.get(0).length();
        char[] gamma = new char[size];
        char[] epsilon = new char[size];

        for (int i = 0; i < size; i++) {
            BitSets bs = getBitSets(numbers, i, size);
            if (bs.getOnes().size() > bs.getZeroes().size()) {
                gamma[i] = '1';
                epsilon[i] = '0';
            } else {
                gamma[i] = '0';
                epsilon[i] = '1';
            }
        }

        return Integer.parseInt(new String(gamma), 2) * Integer.parseInt(new String(epsilon), 2);
    }

    static int part2(List<String> data) {
        List<Integer> generatorNumbers = getNumbers(data);
        List<Integer> co2Numbers = getNumbers(data);
        int size = data.get(0).length();
        int generator = 0;
        int co2 = 0;

        for (int i = 0; i < size; i++) {
            BitSets bs = getBitSets(generatorNumbers, i, size);
            if (bs.getOnes().size() >= bs.getZeroes().size()) {
                generatorNumbers = bs.ones;
            } else {
                generatorNumbers = bs.zeroes;
            }

            if (generatorNumbers.size() == 1) {
                generator = generatorNumbers.get(0);
                break;
            }
        }

        for (int i = 0; i < size; i++) {
            BitSets bs = getBitSets(co2Numbers, i, size);
            if (bs.getOnes().size() < bs.getZeroes().size()) {
                co2Numbers = bs.ones;
            } else {
                co2Numbers = bs.zeroes;
            }

            if (co2Numbers.size() == 1) {
                co2 = co2Numbers.get(0);
                break;
            }
        }

        return generator * co2;
    }

    private static BitSets getBitSets(List<Integer> numbers, int position, int size) {
        int comparer = (int) Math.pow(2, size - position - 1);
        BitSets bs = new BitSets();

        for (int number : numbers) {
            if ((number & comparer) > 0) {
                bs.getOnes().add(number);
            } else {
                bs.getZeroes().add(number);
            }
        }

        return bs;
    }

    private static List<Integer> getNumbers(List<String> data) {
        List<Integer> numbers = new ArrayList<>(data.size());

        for (String binary : data) {
            int number = Integer.parseInt(binary, 2);
            numbers.add(number);
        }

        return numbers;
    }

    private static class BitSets {
        private final List<Integer> zeroes = new ArrayList<>();
        private final List<Integer> ones = new ArrayList<>();

        public List<Integer> getZeroes() {
            return zeroes;
        }

        public List<Integer> getOnes() {
            return ones;
        }
    }
}
