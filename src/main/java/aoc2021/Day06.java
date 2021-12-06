package aoc2021;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day06 {
    public static void main(String[] args) throws FileNotFoundException {
        List<Integer> data = Utils.readCommaSeparatedInputAsIntegerArray("day06.txt");

        System.out.printf("Day 06, part 1: %d\n", part1(data));
        System.out.printf("Day 06, part 2: %d\n", part2(data));
    }

    static long part1(List<Integer> data) {
        return calculateSpawns(data, 80);
    }

    static long part2(List<Integer> data) {
        return calculateSpawns(data, 256);
    }

    static long calculateSpawns(List<Integer> data, int days) {
        Map<Integer, Long> activeSpawnersByDays = new HashMap<>();
        Map<Integer, Long> newSpawnersByDays = new HashMap<>();

        for (Integer state : data) {
            activeSpawnersByDays.merge(state % 7, 1L, Long::sum);
        }

        for (int i = 1; i <= days; i++) {
            int day = i % 7;

            long newSpawners = newSpawnersByDays.getOrDefault(i, 0L);
            newSpawnersByDays.remove(i);
            activeSpawnersByDays.merge(day, newSpawners, Long::sum);

            long activeSpawners = activeSpawnersByDays.get(day);
            newSpawnersByDays.merge(i + 9, activeSpawners, Long::sum);
        }

        long activeSpawners = activeSpawnersByDays.values().stream().reduce(0L, Long::sum);
        long newSpawners = newSpawnersByDays.values().stream().reduce(0L, Long::sum);

        return activeSpawners + newSpawners - newSpawnersByDays.getOrDefault(days + 9, 0L);
    }
}

