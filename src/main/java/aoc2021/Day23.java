package aoc2021;

import java.io.FileNotFoundException;
import java.util.*;

public class Day23 {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> data = Utils.readInputAsStringArray("day23.txt");

        System.out.printf("Day 23, part 1: %d\n", part1(data));
        System.out.printf("Day 23, part 2: %d\n", part2(data));
    }

    static int part1(List<String> data) {
        Chamber chamber = buildChamberMap(data);
        Map<Chamber, Integer> costs = new HashMap<>();
        return dfs(chamber, costs);
    }

    static int part2(List<String> data) {
        return 0;
    }

    private static int dfs(Chamber stateChamber, Map<Chamber, Integer> costs) {
        if (costs.containsKey(stateChamber)) {
            return costs.get(stateChamber);
        }

//        stateChamber.print();

        Chamber chamber = new Chamber(stateChamber);
        int hallwayCost = 0;
        boolean isMoved = true;

        while (isMoved) {
            isMoved = false;

            for (int pos : chamber.hallway.keySet()) {
                char type = chamber.hallway.get(pos);
                if (type == '.') {
                    continue;
                }

                int cost = calculateMoveCost(chamber, type, pos, getHallwayPosition(type));
                if (cost == -1) {
                    continue;
                }

                isMoved = true;
                hallwayCost += cost;

                chamber.hallway.put(pos, '.');
                if (chamber.chambersBottom.get(type) == '.') {
                    chamber.chambersBottom.put(type, type);
                } else {
                    chamber.chambersTop.put(type, type);
                }

//                chamber.print();
            }
        }

        if (chamber.isOrdered()) {
            costs.put(stateChamber, hallwayCost);
            return hallwayCost;
        }

        int bestCost = Integer.MAX_VALUE;

        for (char type : stateChamber.chambersTop.keySet()) {
            char top = chamber.chambersTop.get(type);
            char bottom = chamber.chambersBottom.get(type);

            if (top == type && bottom == type) {
                continue;
            }

            if (top == '.' && (bottom == type || bottom == '.')) {
                continue;
            }

            for (int i : chamber.hallway.keySet()) {
                if (chamber.hallway.get(i) == '.') {
                    char charType = top != '.' ? top : bottom;
                    int cost = calculateMoveCost(chamber, charType, getHallwayPosition(type), i);

                    if (cost == -1) {
                        continue;
                    }

                    Chamber newState = new Chamber(chamber);
                    newState.hallway.put(i, charType);
                    if (top != '.') {
                        newState.chambersTop.put(type, '.');
                    } else {
                        newState.chambersBottom.put(type, '.');
                    }

                    int followingStepsCost = dfs(newState, costs);
                    if (followingStepsCost == -1) {
                        continue;
                    }

                    bestCost = Math.min(bestCost, followingStepsCost + cost + hallwayCost);
                }
            }
        }

        if (bestCost == Integer.MAX_VALUE) {
            return -1;
        }

        costs.put(stateChamber, bestCost);
        return bestCost;
    }

    private static int calculateMoveCost(Chamber chamber, Character type, int start, int end) {
        int current = start;
        int step = Integer.signum(end - start);
        int steps = 0;

        while (current != end) {
            current += step;
            if (chamber.hallway.getOrDefault(current, '.') != '.') {
                return -1;
            }
            steps++;
        }

        if (start == 2 || start == 4 || start == 6 || start == 8) {
            if (chamber.chambersTop.get(getChamberPosition(start)) == type) {
                steps += 1;
            } else {
                steps += 2;
            }
        } else {
            char top = chamber.chambersTop.get(getChamberPosition(end));
            char bottom = chamber.chambersBottom.get(getChamberPosition(end));

            if (top != '.' || (bottom != '.' && bottom != type)) {
                return -1;
            }

            if (bottom == '.') {
                steps += 2;
            } else {
                steps += 1;
            }
        }

        return steps * getCost(type);
    }

    private static int getCost(Character type) {
        switch (type) {
            case 'A': return 1;
            case 'B': return 10;
            case 'C': return 100;
            case 'D': return 1000;
            default: throw new IllegalArgumentException();
        }
    }

    private static int getHallwayPosition(Character type) {
        switch (type) {
            case 'A': return 2;
            case 'B': return 4;
            case 'C': return 6;
            case 'D': return 8;
            default: throw new IllegalArgumentException();
        }
    }

    private static Character getChamberPosition(int hallwayPosition) {
        switch (hallwayPosition) {
            case 2: return 'A';
            case 4: return 'B';
            case 6: return 'C';
            case 8: return 'D';
            default: throw new IllegalArgumentException();
        }
    }

    private static Chamber buildChamberMap(List<String> data) {
        Map<Integer, Character> hallway = new HashMap<>();
        hallway.put(0, '.');
        hallway.put(1, '.');
        hallway.put(3, '.');
        hallway.put(5, '.');
        hallway.put(7, '.');
        hallway.put(9, '.');
        hallway.put(10, '.');

        Map<Character, Character> chambersTop = new HashMap<>();
        Map<Character, Character> chambersBottom = new HashMap<>();

        for (String line : data) {
            int currentChamber = 'A';
            for (int x = 0; x < line.length(); x++) {
                char ch = line.charAt(x);
                if (ch == 'A' || ch == 'B' || ch == 'C' || ch == 'D') {
                    if (chambersTop.size() < 4) {
                        chambersTop.put((char) currentChamber, ch);
                    } else {
                        chambersBottom.put((char) currentChamber, ch);
                    }

                    currentChamber = (currentChamber - 'A' + 1) % 4 + 'A';
                }
            }
        }

        return new Chamber(hallway, chambersTop, chambersBottom);
    }

    private static class Chamber {
        private final Map<Integer, Character> hallway;
        private final Map<Character, Character> chambersTop;
        private final Map<Character, Character> chambersBottom;

        private Chamber(Map<Integer, Character> hallway, Map<Character, Character> chambersTop, Map<Character, Character> chambersBottom) {
            this.hallway = hallway;
            this.chambersTop = chambersTop;
            this.chambersBottom = chambersBottom;
        }

        private Chamber(Chamber otherChamber) {
            this.hallway = new HashMap<>(otherChamber.hallway);
            this.chambersTop = new HashMap<>(otherChamber.chambersTop);
            this.chambersBottom = new HashMap<>(otherChamber.chambersBottom);
        }

        public boolean isOrdered() {
            return chambersTop.get('A') == 'A' && chambersBottom.get('A') == 'A' &&
                chambersTop.get('B') == 'B' && chambersBottom.get('B') == 'B' &&
                chambersTop.get('C') == 'C' && chambersBottom.get('C') == 'C' &&
                chambersTop.get('D') == 'D' && chambersBottom.get('D') == 'D';
        }

        public void print() {
            System.out.printf("\n%s", this);
        }

        @Override
        public String toString() {
            return String.format(
                "#############\n"+
                "#%c%c.%c.%c.%c.%c%c#\n" +
                "###%c#%c#%c#%c###\n" +
                "  #%c#%c#%c#%c#\n" +
                "  #########\n",
                hallway.get(0), hallway.get(1), hallway.get(3), hallway.get(5), hallway.get(7), hallway.get(9), hallway.get(10),
                chambersTop.get('A'), chambersTop.get('B'), chambersTop.get('C'), chambersTop.get('D'),
                chambersBottom.get('A'), chambersBottom.get('B'), chambersBottom.get('C'), chambersBottom.get('D')
            );
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Chamber chamber = (Chamber) o;
            return Objects.equals(hallway, chamber.hallway) && Objects.equals(chambersTop, chamber.chambersTop) && Objects.equals(chambersBottom, chamber.chambersBottom);
        }

        @Override
        public int hashCode() {
            return Objects.hash(hallway, chambersTop, chambersBottom);
        }
    }
}
