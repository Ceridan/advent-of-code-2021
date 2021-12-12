package aoc2021;

import java.io.FileNotFoundException;
import java.util.*;

public class Day09 {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> data = Utils.readInputAsStringArray("day09.txt");

        System.out.printf("Day 09, part 1: %d\n", part1(data));
        System.out.printf("Day 09, part 2: %d\n", part2(data));
    }

    static int part1(List<String> data) {
        Map<Point, Integer> caveMap = buildCaveMap(data);
        int risks = 0;

        for (Map.Entry<Point, Integer> entry : caveMap.entrySet()) {
            Point point = entry.getKey();
            int value = entry.getValue();

            int topValue = caveMap.getOrDefault(new Point(point.x, point.y - 1), 9);
            int rightValue = caveMap.getOrDefault(new Point(point.x + 1, point.y), 9);
            int bottomValue = caveMap.getOrDefault(new Point(point.x, point.y + 1), 9);
            int leftValue = caveMap.getOrDefault(new Point(point.x - 1, point.y), 9);

            if (value < topValue && value < rightValue && value < bottomValue && value < leftValue) {
                risks += value + 1;
            }
        }

        return risks;
    }

    static int part2(List<String> data) {
        Map<Point, Integer> caveMap = buildCaveMap(data);
        Set<Point> visited = new HashSet<>();
        List<Integer> basinSizes = new ArrayList<>();

        for (Point point : caveMap.keySet()) {
            int basinSize = dfs(caveMap, visited, point);

            if (basinSize > 0) {
                basinSizes.add(basinSize);
            }
        }

        basinSizes.sort(Comparator.reverseOrder());
        return basinSizes.get(0) * basinSizes.get(1) * basinSizes.get(2);
    }

    private static int dfs(Map<Point, Integer> caveMap, Set<Point> visited, Point point) {
        if (caveMap.getOrDefault(point, 9) == 9 || visited.contains(point)) {
            return 0;
        }

        visited.add(point);

        return dfs(caveMap, visited, new Point(point.x, point.y - 1)) +
            dfs(caveMap, visited, new Point(point.x + 1, point.y)) +
            dfs(caveMap, visited, new Point(point.x, point.y + 1)) +
            dfs(caveMap, visited, new Point(point.x - 1, point.y)) + 1;
    }

    private static Map<Point, Integer> buildCaveMap(List<String> data) {
        Map<Point, Integer> caveMap = new HashMap<>();

        for (int y = 0; y < data.size(); y++) {
            String line = data.get(y);

            for (int x = 0; x < line.length(); x++) {
                Point point = new Point(x, y);
                caveMap.put(point, Integer.parseInt(line, x, x + 1, 10));
            }
        }

        return caveMap;
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
