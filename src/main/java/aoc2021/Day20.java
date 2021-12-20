package aoc2021;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day20 {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> data = Utils.readInputAsStringArray("day20.txt");

        System.out.printf("Day 20, part 1: %d\n", part1(data));
        System.out.printf("Day 20, part 2: %d\n", part2(data));
    }

    static int part1(List<String> data) {
        return calculateEnhancedImage(data, 2);
    }

    static int part2(List<String> data) {
        return calculateEnhancedImage(data, 50);
    }

    private static int calculateEnhancedImage(List<String> data, int steps) {
        int[] algorithm = buildAlgorithm(data);
        Map<Point, Integer> inputImage = buildInputImage(data);
        int defaultValue = 0;
        int minX = 0, maxX = data.get(2).length();
        int minY = 0, maxY = data.size() - 2;

        for (int step = 0; step < steps; step++) {
            Map<Point, Integer> outputImage = new HashMap<>();
            minX--; maxX++; minY--; maxY++;

            for (int y = minY; y <= maxY; y++) {
                for (int x = minX; x <= maxX; x++) {
                    Point coords = new Point(x, y);
                    outputImage.put(coords, calculatePixel(inputImage, algorithm, defaultValue, coords));
                }
            }

            defaultValue = defaultValue == 0 ? algorithm[0] : algorithm[511];
            inputImage = outputImage;
        }

        return inputImage.values().stream().mapToInt(Integer::intValue).sum();
    }

    private static int calculatePixel(Map<Point, Integer> image, int[] algorithm, int defaultValue, Point coords) {
        int index = 0;
        int multiplier = 1;

        for (int dy = coords.y + 1; dy >= coords.y - 1; dy--) {
            for (int dx = coords.x + 1; dx >= coords.x - 1; dx--) {
                index += image.getOrDefault(new Point(dx, dy), defaultValue) * multiplier;
                multiplier *= 2;
            }
        }

        return algorithm[index];
    }

    private static int[] buildAlgorithm(List<String> data) {
        return data.get(0).chars().map(ch -> ch == '#' ? 1 : 0).toArray();
    }

    private static Map<Point, Integer> buildInputImage(List<String> data) {
        Map<Point, Integer> image = new HashMap<>();
        int y = 0;

        for (int i = 2; i < data.size(); i++) {
            String line = data.get(i);

            for (int x = 0; x < line.length(); x++) {
                image.put(new Point(x, y), line.charAt(x) == '#' ? 1 : 0);
            }

            y++;
        }

        return image;
    }

    private static class Point {
        private final int x;
        private final int y;

        private Point(int x, int y) {
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
