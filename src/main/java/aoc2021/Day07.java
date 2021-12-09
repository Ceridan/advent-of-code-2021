package aoc2021;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.ToIntBiFunction;

public class Day07 {
    public static void main(String[] args) throws FileNotFoundException {
        List<Integer> data = Utils.readCommaSeparatedInputAsIntegerArray("day07.txt");

        System.out.printf("Day 07, part 1: %d\n", part1(data));
        System.out.printf("Day 07, part 2: %d\n", part2(data));
    }

    static int part1(List<Integer> data) {
        Map<Integer, Integer> positionToCount = new HashMap<>();

        for (int position : data) {
            positionToCount.merge(position, 1, Integer::sum);
        }

        int minPosition = Collections.min(positionToCount.keySet());
        int maxPosition = Collections.max(positionToCount.keySet());

        int currentFuel = calculateFuel(positionToCount, minPosition, (p1, p2) -> Math.abs(p1 - p2));
        int bestFuel = currentFuel;

        int leftCount = 0;
        int rightCount = data.size() - positionToCount.get(minPosition);

        for (int i = minPosition + 1; i <= maxPosition; i++) {
            leftCount += positionToCount.getOrDefault(i - 1, 0);
            rightCount -= positionToCount.getOrDefault(i, 0);
            currentFuel += leftCount - rightCount - positionToCount.getOrDefault(i, 0);

            if (currentFuel < bestFuel) {
                bestFuel = currentFuel;
            }
        }

        return bestFuel;
    }

    static int part2(List<Integer> data) {
        Map<Integer, Integer> positionToCount = new HashMap<>();

        for (int position : data) {
            positionToCount.merge(position, 1, Integer::sum);
        }

        int minPosition = Collections.min(positionToCount.keySet());
        int maxPosition = Collections.max(positionToCount.keySet());

        int bestFuel = Integer.MAX_VALUE;

        for (int i = minPosition; i <= maxPosition; i++) {
            int currentFuel = calculateFuel(positionToCount, i, (p1, p2) -> {
                int diff = Math.abs(p1 - p2);
                return diff * (diff + 1) / 2;
            });

            if (currentFuel < bestFuel) {
                bestFuel = currentFuel;
            }
        }

        return bestFuel;
    }

    static int calculateFuel(Map<Integer, Integer> positionToCount, int currentPosition, ToIntBiFunction<Integer, Integer> calculateStepsCost) {
        int fuel = 0;

        for (int position : positionToCount.keySet()) {
            fuel += calculateStepsCost.applyAsInt(position, currentPosition) * positionToCount.get(position);
        }

        return fuel;
    }
}
