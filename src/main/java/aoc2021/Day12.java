package aoc2021;

import java.io.FileNotFoundException;
import java.util.*;

public class Day12 {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> data = Utils.readInputAsStringArray("day12.txt");

        System.out.printf("Day 12, part 1: %d\n", part1(data));
        System.out.printf("Day 12, part 2: %d\n", part2(data));
    }

    static int part1(List<String> data) {
        Map<String, Cave> cavesMap = buildCavesMap(data);
        return countPaths(cavesMap, cavesMap.get("start"), new HashSet<>());
    }

    static int part2(List<String> data) {
        return 0;
    }

    static int countPaths(Map<String, Cave> cavesMap, Cave cave, Set<String> visited) {
        if (cave.name.equals("end")) {
            return 1;
        }

        if (visited.contains(cave.name)) {
            return 0;
        }

        if (!cave.isBigCave()) {
            visited.add(cave.name);
        }

        int pathCount = 0;

        for (Cave neighborCave : cave.getPaths()) {
            pathCount += countPaths(cavesMap, neighborCave, visited);
        }

        visited.remove(cave.name);

        return pathCount;
    }

    private static Map<String, Cave> buildCavesMap(List<String> data) {
        Map<String, Cave> cavesMap = new HashMap<>();

        for (String line : data) {
            String[] caveNames = line.split("-");

            if (!cavesMap.containsKey(caveNames[0])) {
                cavesMap.put(caveNames[0], new Cave(caveNames[0]));
            }
            Cave first = cavesMap.get(caveNames[0]);

            if (!cavesMap.containsKey(caveNames[1])) {
                cavesMap.put(caveNames[1], new Cave(caveNames[1]));
            }
            Cave second = cavesMap.get(caveNames[1]);

            first.getPaths().add(second);
            second.getPaths().add(first);
        }

        return cavesMap;
    }

    private static class Cave {
        private final String name;
        private final boolean size;
        private final List<Cave> paths = new ArrayList<>();

        private Cave(String name) {
            this.name = name;
            this.size = name.equals(name.toUpperCase());
        }

        public boolean isBigCave() {
            return size;
        }

        public String getName() {
            return name;
        }

        public List<Cave> getPaths() {
            return paths;
        }
    }
}
