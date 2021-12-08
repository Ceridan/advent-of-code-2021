package aoc2021;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        List<String> patterns = Arrays.stream(input[0].trim().split(" ")).collect(Collectors.toList());
        String[] outputs = input[1].trim().split(" ");

        Digit[] digits = new Digit[10];

        digits[1] = findDigitByPredicate(patterns, d -> d.getSize() == 2);
        digits[4] = findDigitByPredicate(patterns, d -> d.getSize() == 4);
        digits[7] = findDigitByPredicate(patterns, d -> d.getSize() == 3);
        digits[8] = findDigitByPredicate(patterns, d -> d.getSize() == 7);
        digits[3] = findDigitByPredicate(patterns, d -> d.getSize() == 5 && d.contains(digits[7]));
        digits[9] = findDigitByPredicate(patterns, d -> d.getSize() == 6 && d.contains(digits[4]));
        digits[0] = findDigitByPredicate(patterns, d -> d.getSize() == 6 && !d.contains(digits[4]) && d.contains(digits[7]));
        digits[5] = findDigitByPredicate(patterns, d -> d.getSize() == 5 && digits[9].contains(d) && !d.contains(digits[7]));
        digits[6] = findDigitByPredicate(patterns, d -> d.getSize() == 6 && d.contains(digits[5]) && !d.contains(digits[7]));
        digits[2] = findDigitByPredicate(patterns, d -> d.getSize() == 5 && !d.equals(digits[3]) && !d.equals(digits[5]));

        Map<Digit, Integer> digitToValue = IntStream
            .range(0, digits.length)
            .boxed()
            .collect(Collectors.toMap(i -> digits[i], i -> i));

        int number = 0;
        int modifier = 1;
        for (int i = outputs.length - 1; i >= 0; i--) {
            int value = digitToValue.get(new Digit(outputs[i]));
            number += value * modifier;
            modifier *= 10;
        }

        return number;
    }

    private static Digit findDigitByPredicate(Collection<String> patterns, Predicate<? super Digit> predicate) {
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        Digit digit = patterns
            .stream()
            .map(Digit::new)
            .filter(predicate)
            .findFirst()
            .get();

        return digit;
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

