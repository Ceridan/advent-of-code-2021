package aoc2021;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day22 {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> data = Utils.readInputAsStringArray("day22.txt");

        System.out.printf("Day 22, part 1: %d\n", part1(data));
        System.out.printf("Day 22, part 2: %d\n", part2(data));
    }

    static long part1(List<String> data) {
        List<RebootStep> steps = buildRebootSteps(data);
        List<RebootStep> initializationSteps = steps
            .stream()
            .map(step -> new RebootStep(
                step.switchTo,
                new Cube(
                    Math.max(step.cube.minX, -50), Math.min(step.cube.maxX, 50),
                    Math.max(step.cube.minY, -50), Math.min(step.cube.maxY, 50),
                    Math.max(step.cube.minZ, -50), Math.min(step.cube.maxZ, 50)
                )))
            .filter(step -> step.cube.validate())
            .collect(Collectors.toList());
        return calculateRebootCubes(initializationSteps);
    }

    static long part2(List<String> data) {
        List<RebootStep> steps = buildRebootSteps(data);
        return calculateRebootCubes(steps);
    }

    private static long calculateRebootCubes(List<RebootStep> steps) {
        ArrayList<Cube> turnedOn = new ArrayList<>();

        for (RebootStep step : steps) {
            ArrayList<Cube> newTurnedOn = new ArrayList<>();
            if (step.switchTo.equals("on")) {
                newTurnedOn.add(step.cube);
            }

            for (Cube cube : turnedOn) {
                List<Cube> diff = cube.getDifference(step.cube);
                newTurnedOn.addAll(diff);
            }

            turnedOn = newTurnedOn;
        }

        return turnedOn.stream().mapToLong(Cube::getVolume).sum();
    }

    private static List<RebootStep> buildRebootSteps(List<String> data) {
        List<RebootStep> steps = new ArrayList<>();

        Pattern p = Pattern.compile("(on|off) x=(-?\\d+)..(-?\\d+),y=(-?\\d+)..(-?\\d+),z=(-?\\d+)..(-?\\d+)");

        for (String line : data) {
            Matcher m = p.matcher(line);

            if (m.matches()) {
                String switchTo = m.group(1);
                int minX = Integer.parseInt(m.group(2));
                int maxX = Integer.parseInt(m.group(3));
                int minY = Integer.parseInt(m.group(4));
                int maxY = Integer.parseInt(m.group(5));
                int minZ = Integer.parseInt(m.group(6));
                int maxZ = Integer.parseInt(m.group(7));
                Cube cube = new Cube(minX, maxX, minY, maxY, minZ, maxZ);
                steps.add(new RebootStep(switchTo, cube));
            } else {
                throw new IllegalArgumentException();
            }
        }

        return steps;
    }

    private static class RebootStep {
        private final String switchTo;
        private final Cube cube;

        private RebootStep(String switchTo, Cube cube) {
            this.switchTo = switchTo;
            this.cube = cube;
        }
    }

    private static class Cube {
        private final int minX;
        private final int maxX;
        private final int minY;
        private final int maxY;
        private final int minZ;
        private final int maxZ;

        private Cube(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
            this.minZ = minZ;
            this.maxZ = maxZ;
        }

        public long getVolume() {
            return (maxX - minX + 1L) * (maxY - minY + 1L) * (maxZ - minZ + 1L);
        }

        public boolean validate() {
            return minX <= maxX && minY <= maxY && minZ <= maxZ;
        }

        public List<Cube> getDifference(Cube otherCube) {
            Cube intersection = getIntersection(otherCube);
            if (intersection == null) return List.of(this);

            List<Cube> cubes = List.of(
                new Cube(minX, maxX, minY, maxY, minZ, intersection.minZ - 1),
                new Cube(minX, maxX, minY, maxY, intersection.maxZ + 1, maxZ),
                new Cube(minX, maxX, minY, intersection.minY - 1, intersection.minZ, intersection.maxZ),
                new Cube(minX, maxX, intersection.maxY + 1, maxY, intersection.minZ, intersection.maxZ),
                new Cube(minX, intersection.minX - 1, intersection.minY, intersection.maxY, intersection.minZ, intersection.maxZ),
                new Cube(intersection.maxX + 1, maxX, intersection.minY, intersection.maxY, intersection.minZ, intersection.maxZ)
            );

            return cubes
                .stream()
                .filter(Cube::validate)
                .collect(Collectors.toList());
        }

        private Cube getIntersection(Cube otherCube) {
            if (minX > otherCube.maxX || otherCube.minX > maxX) return null;
            if (minY > otherCube.maxY || otherCube.minY > maxY) return null;
            if (minZ > otherCube.maxZ || otherCube.minZ > maxZ) return null;

            return new Cube(
                Math.max(minX, otherCube.minX), Math.min(maxX, otherCube.maxX),
                Math.max(minY, otherCube.minY), Math.min(maxY, otherCube.maxY),
                Math.max(minZ, otherCube.minZ), Math.min(maxZ, otherCube.maxZ));
        }
    }
}
