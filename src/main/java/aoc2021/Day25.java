package aoc2021;

import java.io.FileNotFoundException;
import java.util.*;

public class Day25 {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> data = Utils.readInputAsStringArray("day25.txt");

        System.out.printf("Day 25, part 1: %d\n", part1(data));
        System.out.println("Day 25, part 2: -");
    }

    static int part1(List<String> data) {
        SeaMap map = buildSeaMap(data);
        int step = 0;
        boolean canMove = true;

        while (canMove) {
            canMove = false;

            Set<Point> newEast = new HashSet<>();
            Set<Point> newSouth = new HashSet<>();

            for (Point point : map.east) {
                Point movePoint = new Point((point.x + 1) % map.eastSize, point.y);
                if (map.east.contains(movePoint) || map.south.contains(movePoint)) {
                    newEast.add(point);
                } else {
                    newEast.add(movePoint);
                    canMove = true;
                }
            }

            for (Point point : map.south) {
                Point movePoint = new Point(point.x, (point.y + 1) % map.southSize);
                if (newEast.contains(movePoint) || map.south.contains(movePoint)) {
                    newSouth.add(point);
                } else {
                    newSouth.add(movePoint);
                    canMove = true;
                }
            }

            map.east = newEast;
            map.south = newSouth;

            step++;
        }

        return step;
    }

    static SeaMap buildSeaMap(List<String> data) {
        Set<Point> east = new HashSet<>();
        Set<Point> south = new HashSet<>();


        for (int y = 0; y < data.size(); y++) {
            String line = data.get(y);

            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '>') {
                    east.add(new Point(x, y));
                } else if (line.charAt(x) == 'v') {
                    south.add(new Point(x, y));
                }
            }
        }

        return new SeaMap(east, data.get(0).length(), south, data.size());
    }

    private static class SeaMap {
        private Set<Point> east;
        private final int eastSize;
        private Set<Point> south;
        private final int southSize;

        private SeaMap(Set<Point> east, int eastSize, Set<Point> south, int southSize) {
            this.east = east;
            this.eastSize = eastSize;
            this.south = south;
            this.southSize = southSize;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            for (int y = 0; y < southSize; y++) {
                for (int x = 0; x < eastSize; x++) {
                    Point point = new Point(x, y);
                    if (east.contains(point)) {
                        sb.append('>');
                    } else if (south.contains(point)) {
                        sb.append('v');
                    } else {
                        sb.append('.');
                    }
                }
                sb.append('\n');
            }

            return sb.toString();
        }
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
