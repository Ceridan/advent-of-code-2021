package aoc2021;

import java.io.FileNotFoundException;
import java.util.*;

public class Day11 {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> data = Utils.readInputAsStringArray("day11.txt");
        int steps = 100;

        System.out.printf("Day 11, part 1: %d\n", part1(data, steps));
        System.out.printf("Day 11, part 2: %d\n", part2(data));
    }

    static int part1(List<String> data, int steps) {
        Map<Point, Integer> octopusMap = buildOctopusMap(data);
        int totalFlashCount = 0;

        for (int i = 1; i <= steps; i++) {
            totalFlashCount += calculateStep(octopusMap);
        }

        return totalFlashCount;
    }

    static int part2(List<String> data) {
        Map<Point, Integer> octopusMap = buildOctopusMap(data);
        int step = 1;

        while (calculateStep(octopusMap) < octopusMap.size()) {
            step++;
        }

        return step;
    }

    private static int calculateStep(Map<Point, Integer> octopusMap) {
        Queue<Point> queue = new ArrayDeque<>(octopusMap.size());
        Set<Point> flashed = new HashSet<>();
        int flashCount = 0;

        for (Map.Entry<Point, Integer> entry : octopusMap.entrySet()) {
            Point point = entry.getKey();
            int newEnergy = (entry.getValue() + 1) % 10;
            octopusMap.put(point, newEnergy);

            if (newEnergy == 0) {
                flashed.add(point);
                queue.add(point);
                flashCount++;
            }
        }

        while (!queue.isEmpty()) {
            Point point = queue.remove();

            for (int dy = -1; dy <= 1; dy++) {
                for (int dx = -1; dx <= 1; dx++) {
                    Point nextPoint = new Point(point.x + dx, point.y + dy);

                    if (flashed.contains(nextPoint) || !octopusMap.containsKey(nextPoint)) {
                        continue;
                    }

                    int newEnergy = (octopusMap.get(nextPoint) + 1) % 10;
                    octopusMap.put(nextPoint, (octopusMap.get(nextPoint) + 1) % 10);

                    if (newEnergy == 0) {
                        flashed.add(nextPoint);
                        queue.add(nextPoint);
                        flashCount++;
                    }
                }
            }
        }

        return flashCount;
    }

    private static Map<Point, Integer> buildOctopusMap(List<String> data) {
        Map<Point, Integer> octopusMap = new HashMap<>();

        for (int y = 0; y < data.size(); y++) {
            String line = data.get(y);

            for (int x = 0; x < line.length(); x++) {
                Point point = new Point(x, y);
                octopusMap.put(point, Integer.parseInt(line, x, x + 1, 10));
            }
        }

        return octopusMap;
    }

    private static class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
