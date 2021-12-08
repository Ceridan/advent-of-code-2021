package aoc2021;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Day08 {
    public static void main(String[] args) throws FileNotFoundException {
        List<String> data = Utils.readInputAsStringArray("day08.txt");

        System.out.printf("Day 08, part 1: %d\n", part1(data));
        System.out.printf("Day 08, part 2: %d\n", part2(data));
    }

    static int part1(List<String> data) {
        int count1478 = 0;
        Set<Integer> uniqueNumbers = new HashSet<>(List.of(2, 3, 4, 7));

        for (String line : data) {
            String[] outputs = line.split("\\|")[1].trim().split(" ");

            for (String output : outputs) {
                if (uniqueNumbers.contains(output.length())) {
                    count1478++;
                }
            }
        }

        return count1478;
    }

    static int part2(List<String> data) {
        int total = 0;

        for (String patternWithOutput : data) {
            total += parsePattern(patternWithOutput);
        }

        return total;
    }

    private static int parsePattern(String patternWithOutput) {
        String[] input = patternWithOutput.split("\\|");
        Set<String> patterns = Arrays.stream(input[0].trim().split(" ")).collect(Collectors.toSet());
        String[] outputs = input[1].trim().split(" ");

        Digit[] digits = new Digit[10];
        Map<Digit, Integer> digitToValue = new HashMap<>();

        // Search for 1, 4, 7, 8
        Iterator<String> iterator = patterns.iterator();
        while (iterator.hasNext()) {
            String pattern = iterator.next();
            switch (pattern.length()) {
                case 2:
                    digits[1] = new Digit(pattern);
                    digitToValue.put(digits[1], 1);
                    iterator.remove();
                    break;
                case 3:
                    digits[7] = new Digit(pattern);
                    digitToValue.put(digits[7], 7);
                    iterator.remove();
                    break;
                case 4:
                    digits[4] = new Digit(pattern);
                    digitToValue.put(digits[4], 4);
                    iterator.remove();
                    break;
                case 7:
                    digits[8] = new Digit(pattern);
                    digitToValue.put(digits[8], 8);
                    iterator.remove();
                    break;
            }
        }

        // Search for 0, 3, 9
        iterator = patterns.iterator();
        while (iterator.hasNext()) {
            String pattern = iterator.next();
            var digit = new Digit(pattern);

            if (digit.getSize() == 5 && digit.contains(digits[1])) {
                digits[3] = digit;
                digitToValue.put(digits[3], 3);
                iterator.remove();
            }

            else if (digit.getSize() == 6 && digit.contains(digits[4])) {
                digits[9] = digit;
                digitToValue.put(digits[9], 9);
                iterator.remove();
            }

            else if (digit.getSize() == 6 && digit.contains(digits[7])) {
                digits[0] = digit;
                digitToValue.put(digits[0], 0);
                iterator.remove();
            }
        }

        // Search for 5
        iterator = patterns.iterator();
        while (iterator.hasNext()) {
            String pattern = iterator.next();
            var digit = new Digit(pattern);

            if (digit.getSize() == 5 && digits[9].contains(digit)) {
                digits[5] = digit;
                digitToValue.put(digits[5], 5);
                iterator.remove();
            }
        }

        // Search for 6
        iterator = patterns.iterator();
        while (iterator.hasNext()) {
            String pattern = iterator.next();
            var digit = new Digit(pattern);

            if (digit.getSize() == 6 && digit.contains(digits[5])) {
                digits[6] = digit;
                digitToValue.put(digits[6], 6);
                iterator.remove();
            }
        }

        // Search for 2
        digits[2] = new Digit(patterns.toArray(String[]::new)[0]);
        digitToValue.put(digits[2], 2);

        int number = 0;
        int modifier = 1;
        for (int i = outputs.length - 1; i >= 0; i--) {
            Digit digit = new Digit(outputs[i]);
            int value = digitToValue.get(digit);
            number += value * modifier;
            modifier *= 10;
        }

        return number;
    }


    private static class Digit {
        private final String digit;
        private final Set<Character> digitCharSet;

        private Digit(String pattern) {
            char[] chars = pattern.toCharArray();
            Arrays.sort(chars);
            digit = new String(chars);
            digitCharSet = digit.chars().mapToObj(it -> (char) it).collect(Collectors.toSet());
        }

        public String getDigit() {
            return digit;
        }

        public int getSize() {
            return digit.length();
        }

        public Set<Character> getDigitCharSet() {
            return digitCharSet;
        }

        public boolean contains(Digit otherDigit) {
            return digitCharSet.containsAll(otherDigit.getDigitCharSet());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Digit otherDigit = (Digit) o;
            return Objects.equals(digit, otherDigit.digit);
        }

        @Override
        public int hashCode() {
            return Objects.hash(digit);
        }
    }
}

