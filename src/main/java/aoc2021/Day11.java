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
        int[][] octopusMap = buildOctopusMap(data);
        int totalFlashCount = 0;

        for (int i = 1; i <= steps; i++) {
            totalFlashCount += calculateStep(octopusMap);
        }

        return totalFlashCount;
    }

    static int part2(List<String> data) {
        int[][] octopusMap = buildOctopusMap(data);
        int octopusMapSize = (octopusMap.length - 2) * (octopusMap[0].length - 2);
        int step = 1;

        while (calculateStep(octopusMap) < octopusMapSize) {
            step++;
        }

        return step;
    }

    private static int calculateStep(int[][] octopusMap) {
        Queue<Point> queue = new ArrayDeque<>((octopusMap.length - 2) * (octopusMap[0].length - 2));
        Set<Point> flashed = new HashSet<>();
        int flashCount = 0;

        for (int y = 1; y < octopusMap.length - 1; y++) {
            for (int x = 1; x < octopusMap[0].length - 1; x++) {
                octopusMap[y][x] = (octopusMap[y][x] + 1) % 10;

                if (octopusMap[y][x] == 0) {
                    Point point = new Point(x, y);
                    flashed.add(point);
                    queue.add(point);
                    flashCount++;
                }
            }
        }

        while (!queue.isEmpty()) {
            Point point = queue.remove();

            for (int dy = -1; dy <= 1; dy++) {
                for (int dx = -1; dx <= 1; dx++) {
                    Point nextPoint = new Point(point.x + dx, point.y + dy);

                    if (flashed.contains(nextPoint) || octopusMap[nextPoint.y][nextPoint.x] == -1) {
                        continue;
                    }

                    octopusMap[nextPoint.y][nextPoint.x] = (octopusMap[nextPoint.y][nextPoint.x] + 1) % 10;

                    if (octopusMap[nextPoint.y][nextPoint.x] == 0) {
                        flashed.add(nextPoint);
                        queue.add(nextPoint);
                        flashCount++;
                    }
                }
            }
        }

        return flashCount;
    }

    private static int[][] buildOctopusMap(List<String> data) {
        int[][] octopusMap = new int[data.size() + 2][data.get(0).length() + 2];

        for (int x = 0; x < octopusMap[0].length; x++) {
            octopusMap[0][x] = -1;
            octopusMap[octopusMap.length - 1][x] = -1;
        }

        for (int y = 0; y < octopusMap.length; y++) {
            octopusMap[y][0] = -1;
            octopusMap[y][octopusMap[0].length - 1] = -1;
        }

        for (int y = 1; y < octopusMap.length - 1; y++) {
            String line = data.get(y - 1);

            for (int x = 1; x < octopusMap[0].length - 1; x++) {
                octopusMap[y][x] = Integer.parseInt(line, x - 1, x, 10);
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
