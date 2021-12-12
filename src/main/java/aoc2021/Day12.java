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
        VisitedMonitor vm = new VisitedMonitor(cavesMap);
        return countPaths(cavesMap.get("start"), vm);
    }

    static int part2(List<String> data) {
        Map<String, Cave> cavesMap = buildCavesMap(data);
        int totalPathCount = 0;

        VisitedMonitor vm = new VisitedMonitor(cavesMap);
        int singlePassPathCount = countPaths(cavesMap.get("start"), vm);
        totalPathCount += singlePassPathCount;

        for (String caveName : cavesMap.keySet()) {
            if (caveName.equals("start") || caveName.equals("end") || caveName.equals(caveName.toUpperCase())) {
                continue;
            }

            vm = new VisitedMonitor(cavesMap, caveName);
            int pathCount = countPaths(cavesMap.get("start"), vm);
            totalPathCount += pathCount - singlePassPathCount;
        }

        return totalPathCount;
    }

    private static int countPaths(Cave cave, VisitedMonitor vm) {
        if (cave.name.equals("end")) {
            return 1;
        }

        if (vm.contains(cave.name)) {
            return 0;
        }

        vm.add(cave.name);

        int pathCount = 0;

        for (Cave neighborCave : cave.getPaths()) {
            pathCount += countPaths(neighborCave, vm);
        }

        vm.remove(cave.name);

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
        private final List<Cave> paths = new ArrayList<>();

        private Cave(String name) {
            this.name = name;
        }

        public List<Cave> getPaths() {
            return paths;
        }
    }

    private static class VisitedMonitor {
        private final Map<String, Integer> visited = new HashMap<>();
        private final Map<String, Integer> limits = new HashMap<>();

        public VisitedMonitor(Map<String, Cave> cavesMap) {
            for (String caveName : cavesMap.keySet()) {
                if (caveName.equals(caveName.toLowerCase())) {
                    visited.put(caveName, 0);
                    limits.put(caveName, 1);
                }

            }
        }

        public VisitedMonitor(Map<String, Cave> cavesMap, String twiceLimitCave) {
            this(cavesMap);
            limits.put(twiceLimitCave, 2);
        }

        public boolean contains(String name) {
            if (!visited.containsKey(name)) {
                return false;
            }

            return Objects.equals(visited.get(name), limits.get(name));
        }

        public void add(String name) {
            if (visited.containsKey(name)) {
                visited.merge(name, 1, Integer::sum);
            }
        }

        public void remove(String name) {
            if (visited.containsKey(name)) {
                visited.merge(name, -1, Integer::sum);
            }
        }
    }
}
