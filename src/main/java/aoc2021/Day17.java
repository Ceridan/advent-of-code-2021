package aoc2021;

import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day17 {

    public static void main(String[] args) throws FileNotFoundException {
        String data = Utils.readInputAsString("day17.txt");

        System.out.printf("Day 17, part 1: %d\n", part1(data));
        System.out.printf("Day 17, part 2: %d\n", part2(data));
    }

    static int part1(String data) {
        PointRange range = buildTargetArea(data);
        Point start = range.min.x > 0
            ? new Point(0, 0)
            : new Point(2 * range.max.x - (range.max.x - range.min.x), 0);
        int bestY = Integer.MIN_VALUE;

        for (int vx = start.x; vx <= range.max.x; vx++){
            for (int vy = range.max.y; vy <= Math.abs(start.x - range.max.y); vy++) {
                Point velocity = new Point(vx, vy);
                Point current = new Point(start.x, start.y);
                int maxY = Integer.MIN_VALUE;

                do {
                    current.x += velocity.x;
                    current.y += velocity.y;
                    velocity.x = (velocity.x == 0) ? 0 : velocity.x - 1;
                    velocity.y--;
                    maxY = Math.max(current.y, maxY);
                } while (!range.IsMissing(current) && !range.IsInRange(current));

                if (range.IsInRange(current)) {
                    bestY = Math.max(bestY, maxY);
                }
            }
        }

        return bestY;
    }

    static int part2(String data) {
        PointRange range = buildTargetArea(data);
        Point start = range.min.x > 0
            ? new Point(0, 0)
            : new Point(2 * range.max.x - (range.max.x - range.min.x), 0);
        int velocityCount = 0;

        for (int vx = start.x; vx <= range.max.x; vx++){
            for (int vy = range.max.y; vy <= Math.abs(start.x - range.max.y); vy++) {
                Point velocity = new Point(vx, vy);
                Point current = new Point(start.x, start.y);
                do {
                    current.x += velocity.x;
                    current.y += velocity.y;
                    velocity.x = (velocity.x == 0) ? 0 : velocity.x - 1;
                    velocity.y--;
                } while (!range.IsMissing(current) && !range.IsInRange(current));

                if (range.IsInRange(current)) {
                    velocityCount++;
                }
            }
        }

        return velocityCount;
    }

    private static PointRange buildTargetArea(String data) {
        Pattern p = Pattern.compile("=(-?\\d+)\\.\\.(-?\\d+).+=(-?\\d+)\\.\\.(-?\\d+)");
        Matcher m = p.matcher(data);
        if (m.find()) {
            Point startPoint = new Point(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(4)));
            Point endPoint = new Point(Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)));
            return new PointRange(startPoint, endPoint);
        }

        throw new IllegalArgumentException();
    }

    private static class PointRange {
        private final Point min;
        private final Point max;

        public PointRange(Point min, Point max) {
            this.min = min;
            this.max = max;
        }

        public boolean IsInRange(Point point) {
            return min.x <= point.x &&
                max.x >= point.x &&
                min.y >= point.y &&
                max.y <= point.y;
        }

        public boolean IsMissing(Point point) {
            return max.y > point.y || max.x < point.x;
        }
    }

    private static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
