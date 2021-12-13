package aoc2021;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Day13 {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> data = Utils.readInputAsStringArray("day13.txt");

        System.out.printf("Day 13, part 1: %d\n", part1(data));
        System.out.printf("Day 13, part 2:\n%s", part2(data));
    }

    static int part1(List<String> data) {
        Paper paper = new Paper(data);
        List<Instruction> instructions = Instruction.buildInstructions(data);

        paper.fold(instructions.get(0));
        return paper.countDots();
    }

    static String part2(List<String> data) {
        Paper paper = new Paper(data);
        List<Instruction> instructions = Instruction.buildInstructions(data);

        for (Instruction instruction : instructions) {
            paper.fold(instruction);
        }

        return paper.print();
    }

    private static class Instruction {
        private final char direction;
        private final int value;

        private Instruction(char direction, int value) {
            this.direction = direction;
            this.value = value;
        }

        public static List<Instruction> buildInstructions(List<String> data) {
            List<String> instrcts = data
                .stream()
                .filter(line -> line.startsWith("fold"))
                .collect(Collectors.toList());

            List<Instruction> instructions = new ArrayList<>();

            for (String instrct : instrcts) {
                String[] instrctParts = instrct.split("=");
                char direction = instrctParts[0].charAt(instrctParts[0].length() - 1);
                int value = Integer.parseInt(instrctParts[1]);

                instructions.add(new Instruction(direction, value));
            }

            return instructions;
        }
    }

    private static class Paper {
        private Map<Point, Integer> paper = new HashMap<>();

        public Paper(List<String> data) {
            init(data);
        }

        public int countDots() {
            return paper.size();
        }


        public String print() {
            Point max = getBottomRightDot();
            StringBuilder sb = new StringBuilder();
            sb.append("\n");

            for (int y = 0; y <= max.y; y++) {
                for (int x = 0; x <= max.x; x++) {
                    Point point = new Point(x, y);

                    if (paper.containsKey(point)) {
                        sb.append("#");
                    } else {
                        sb.append(" ");
                    }
                }
                sb.append("\n");
            }

            sb.append("\n");

            return sb.toString();
        }

        public void fold(Instruction instruction) {
            if (instruction.direction == 'y') {
                foldUp(instruction.value);
            } else {
                foldLeft(instruction.value);
            }
        }

        private void foldUp(int foldY) {
            Map<Point, Integer> newPaper = new HashMap<>();

            for (Point point : paper.keySet()) {
                if (point.y < foldY) {
                    newPaper.put(point, paper.get(point));
                } else {
                    Point newPoint = new Point(point.x, 2 * foldY - point.y);
                    newPaper.put(newPoint, paper.getOrDefault(newPoint, 0) + 1);
                }
            }

            paper = newPaper;
        }

        private void foldLeft(int foldX) {
            Map<Point, Integer> newPaper = new HashMap<>();

            for (Point point : paper.keySet()) {
                if (point.x < foldX) {
                    newPaper.put(point, paper.get(point));
                } else {
                    Point newPoint = new Point(2 * foldX - point.x, point.y);
                    newPaper.put(newPoint, paper.getOrDefault(newPoint, 0) + 1);
                }
            }

            paper = newPaper;
        }

        private Point getBottomRightDot() {
            int maxX = 0;
            int maxY = 0;

            for (Point point : paper.keySet()) {
                maxX = Math.max(maxX, point.x);
                maxY = Math.max(maxY, point.y);
            }

            return new Point(maxX, maxY);
        }

        private void init(List<String> data) {
            List<String> dots = data
                .stream()
                .filter(line -> !line.isBlank() && !line.startsWith("fold"))
                .collect(Collectors.toList());

            for (String line : dots) {
                String[] coords = line.split(",");
                Point point = new Point(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));

                paper.put(point, 1);
            }
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
