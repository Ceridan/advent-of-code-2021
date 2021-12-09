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
        int[][] caveMap = buildCaveMap(data);
        int risks = 0;

        for (int y = 1; y < caveMap.length - 1; y++) {
            for (int x = 1; x < caveMap[0].length - 1; x++) {
                int val = caveMap[y][x];

                if (val < caveMap[y - 1][x] && val < caveMap[y + 1][x] && val < caveMap[y][x - 1] && val < caveMap[y][x + 1]) {
                    risks += val + 1;
                }
            }
        }

        return risks;
    }

    static int part2(List<String> data) {
        int[][] caveMap = buildCaveMap(data);
        Set<Point> visited = new HashSet<>();
        List<Integer> basinSizes = new ArrayList<>();

        for (int y = 1; y < caveMap.length - 1; y++) {
            for (int x = 1; x < caveMap[0].length - 1; x++) {
                Point point = new Point(x, y);
                int basinSize = dfs(caveMap, visited, point);

                if (basinSize > 0) {
                    basinSizes.add(basinSize);
                }
            }
        }

        basinSizes.sort(Comparator.reverseOrder());
        return basinSizes.get(0) * basinSizes.get(1) * basinSizes.get(2);
    }

    private static int dfs(int[][] caveMap, Set<Point> visited, Point point) {
        if (caveMap[point.y][point.x] >= 9 || visited.contains(point)) {
            return 0;
        }

        visited.add(point);

        return dfs(caveMap, visited, new Point(point.x - 1, point.y)) +
            dfs(caveMap, visited, new Point(point.x + 1, point.y)) +
            dfs(caveMap, visited, new Point(point.x, point.y - 1)) +
            dfs(caveMap, visited, new Point(point.x, point.y + 1)) + 1;
    }

    private static int[][] buildCaveMap(List<String> data) {
        int[][] caveMap = new int[data.size() + 2][data.get(0).length() + 2];

        for (int x = 0; x < caveMap[0].length; x++) {
            caveMap[0][x] = Integer.MAX_VALUE;
            caveMap[caveMap.length - 1][x] = Integer.MAX_VALUE;
        }

        for (int y = 0; y < caveMap.length; y++) {
            caveMap[y][0] = Integer.MAX_VALUE;
            caveMap[y][caveMap[0].length - 1] = Integer.MAX_VALUE;
        }

        for (int y = 1; y < caveMap.length - 1; y++) {
            CharSequence chars = data.get(y - 1);

            for (int x = 1; x < caveMap[0].length - 1; x++) {
                caveMap[y][x] = Integer.parseInt(chars, x - 1, x, 10);
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

