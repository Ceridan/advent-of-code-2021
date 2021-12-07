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

        ArrayList<Integer> sortedPositions = new ArrayList<>(positionToCount.keySet());
        sortedPositions.sort(Comparator.naturalOrder());

        int currentFuel = calculateFuel(positionToCount, sortedPositions.get(0), (p1, p2) -> Math.abs(p1 - p2));
        int bestFuel = currentFuel;

        int leftCount = 0;
        int rightCount = data.size() - positionToCount.get(sortedPositions.get(0));

        for (int i = 1; i < sortedPositions.size(); i++) {
            int prevPosition = sortedPositions.get(i - 1);
            int currentPosition = sortedPositions.get(i);
            int diff = currentPosition - prevPosition;

            leftCount += positionToCount.get(prevPosition);
            rightCount -= positionToCount.get(currentPosition);
            currentFuel += leftCount * diff - rightCount * diff - positionToCount.get(currentPosition) * diff;

            if (currentFuel < bestFuel) {
                bestFuel = currentFuel;
            }
        }

        return bestFuel;
    }

    static int part2(List<Integer> data) {
        Map<Integer, Integer> positionToCount = new HashMap<>();

        int minPosition = Integer.MAX_VALUE;
        int maxPosition = 0;

        for (int position : data) {
            positionToCount.merge(position, 1, Integer::sum);

            if (position < minPosition) {
                minPosition = position;
            }

            if (position > maxPosition) {
                maxPosition = position;
            }
        }

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

