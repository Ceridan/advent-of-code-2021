package aoc2021;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day19 {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> data = Utils.readInputAsStringArray("day19.txt");

        System.out.printf("Day 19, part 1: %d\n", part1(data));
        System.out.printf("Day 19, part 2: %d\n", part2(data));
    }

    static int part1(List<String> data) {
        HashMap<Integer, Scanner> scanners = buildScanners(data);
        Set<Integer> visited = new HashSet<>(scanners.size());
        List<Direction> directions = getDirections();

        dfs(scanners, visited, 0, directions);

        Set<Point> uniqueBeacons = new HashSet<>();
        for (int i = 0; i < scanners.size(); i++) {
            Scanner scanner = scanners.get(i);
            uniqueBeacons.addAll(scanner.getBeaconAbsolutePositions());
        }

        return uniqueBeacons.size();
    }

    static void dfs(HashMap<Integer, Scanner> scanners, Set<Integer> visited, int scannerId, List<Direction> directions) {
        if (visited.contains(scannerId)) {
            return;
        }

        visited.add(scannerId);
        Scanner scanner = scanners.get(scannerId);

        for (int i = 0; i < scanners.size(); i++) {
            if (visited.contains(i)) {
                continue;
            }

            if (searchOverlap(scanner, scanners.get(i), directions)) {
                dfs(scanners, visited, i, directions);
            }
        }
    }

    static boolean searchOverlap(Scanner sourceScanner, Scanner targetScanner, List<Direction> directions) {
        HashMap<Point, Integer> targetPositions;

        for (Direction direction : directions) {
            targetPositions = new HashMap<>();
            targetScanner.rotate(direction);

            for (Point sourceBeacon : sourceScanner.beacons) {
                for (Point targetBeacon : targetScanner.beacons) {
                    Point position = new Point(
                        sourceScanner.position.x + sourceBeacon.x - targetBeacon.x,
                        sourceScanner.position.y + sourceBeacon.y - targetBeacon.y,
                        sourceScanner.position.z + sourceBeacon.z - targetBeacon.z);

                    if (targetPositions.merge(position, 1, Integer::sum) == 12) {
                        targetScanner.position = position;
                        targetScanner.direction = new Direction(1, 1, 1, 0);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    static int part2(List<String> data) {
        return 0;
    }

    private static List<Direction> getDirections() {
        ArrayList<Direction> directions = new ArrayList<>(24);
        for (int shift = 0; shift <= 2; shift++) {
            for (int x : List.of(-1, 1)) {
                for (int y : List.of(-1, 1)) {
                    for (int z : List.of(-1, 1)) {
                        directions.add(new Direction(x, y, z, shift));
                    }
                }
            }
        }
        return directions;
    }

    private static HashMap<Integer, Scanner> buildScanners(List<String> data) {
        HashMap<Integer, Scanner> scanners = new HashMap<>();
        Scanner scanner = null;
        Pattern sensorPattern = Pattern.compile("--- scanner (\\d+) ---");
        Pattern beaconPattern = Pattern.compile("(-?\\d+),(-?\\d+),(-?\\d+)");

        for (String line : data) {
            if (line.isEmpty()) {
                continue;
            }

            Matcher m = sensorPattern.matcher(line);

            if (m.matches()) {
                int id = Integer.parseInt(m.group(1));
                scanner = new Scanner(id);
                scanners.put(id, scanner);
                continue;
            }

            m = beaconPattern.matcher(line);
            if (m.matches() && scanner != null) {
                scanner.beacons.add(new Point(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3))));
            }
        }

        return scanners;
    }

    private static class Scanner {
        private final int id;
        private final List<Point> beacons = new ArrayList<>();
        private Point position = new Point(0, 0, 0);
        private Direction direction = new Direction(1, 1, 1, 0);

        public Scanner(int id) {
            this.id = id;
        }

        public void rotate(Direction newDirection) {
            for (Point beacon : beacons) {
                int bx = beacon.x * direction.x;
                int by = beacon.y * direction.y;
                int bz = beacon.z * direction.z;

                int shiftDiff = (3 + newDirection.shift - direction.shift) % 3;

                if (shiftDiff == 1) {
                    int tmp = bz;
                    bz = by;
                    by = bx;
                    bx = tmp;
                } else if (shiftDiff == 2) {
                    int tmp = bx;
                    bx = by;
                    by = bz;
                    bz = tmp;
                }

                beacon.x = bx * newDirection.x;
                beacon.y = by * newDirection.y;
                beacon.z = bz * newDirection.z;
            }

            direction.x = newDirection.x;
            direction.y = newDirection.y;
            direction.z = newDirection.z;
            direction.shift = newDirection.shift;
        }

        public List<Point> getBeaconAbsolutePositions() {
            return beacons
                .stream()
                .map(p -> new Point(position.x + p.x, position.y + p.y, position.z + p.z))
                .collect(Collectors.toList());
        }
    }

    private static class Direction extends Point {
        private int shift;

        public Direction(int x, int y, int z, int shift) {
            super(x, y, z);
            this.shift = shift;
        }
    }

    private static class Point {
        protected int x;
        protected int y;
        protected int z;

        public Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y && z == point.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }
}
