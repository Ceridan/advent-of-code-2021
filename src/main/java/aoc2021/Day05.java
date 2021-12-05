package aoc2021;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day05 {
    public static void main(String[] args) throws FileNotFoundException {
        List<String> data = Utils.readInputAsStringArray("day05.txt");

        System.out.printf("Day 05, part 1: %d\n", part1(data));
        System.out.printf("Day 05, part 2: %d\n", part2(data));
    }

    static int part1(List<String> data) {
        Board board = new Board();
        List<Line> lines = Line.parseData(data).stream()
            .filter(line -> line.start.x == line.end.x || line.start.y == line.end.y)
            .collect(Collectors.toList());

        for (Line line : lines) {
            board.addLine(line);
        }

        return board.getIntersectionCount();
    }

    static int part2(List<String> data) {
        Board board = new Board();
        List<Line> lines = Line.parseData(data).stream()
            .filter(line -> {
                int deltaX = Math.abs(line.start.x - line.end.x);
                int deltaY = Math.abs(line.start.y - line.end.y);
                return line.start.x == line.end.x || line.start.y == line.end.y || deltaX == deltaY;
            })
            .collect(Collectors.toList());

        for (Line line : lines) {
            board.addLine(line);
        }

        return board.getIntersectionCount();
    }

    private static class Board {
        private final Map<Point, Integer> board = new HashMap<>();
        private int intersectionCount = 0;

        public void addLine(Line line) {
            int deltaX = Math.abs(line.start.x - line.end.x);
            int deltaY = Math.abs(line.start.y - line.end.y);
            int stepX = Integer.signum(line.end.x - line.start.x);
            int stepY = Integer.signum(line.end.y - line.start.y);
            Point coord = new Point(line.start.x, line.start.y);

            for (int i = 0; i <= Math.max(deltaX, deltaY); i++) {
                int value = board.getOrDefault(coord, 0);
                if (value == 1) {
                    intersectionCount++;
                }
                board.put(coord, value + 1);


                coord = new Point(coord.x + stepX, coord.y + stepY);
            }
        }

        public int getIntersectionCount() {
            return intersectionCount;
        }
    }

    private static class Line {
        private final Point start;
        private final Point end;

        private Line(Point start, Point end) {
            this.start = start;
            this.end = end;
        }

        public static List<Line> parseData(List<String> data) {
            ArrayList<Line> lines = new ArrayList<>();
            Pattern p = Pattern.compile("(\\d+),(\\d+) -> (\\d+),(\\d+)");

            for (String line : data) {
                Matcher m = p.matcher(line);
                if (m.matches()) {
                    Point startPoint = new Point(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
                    Point endPoint = new Point(Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)));
                    lines.add(new Line(startPoint, endPoint));
                }
            }

            return lines;
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
