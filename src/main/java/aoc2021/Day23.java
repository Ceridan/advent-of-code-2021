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
        ChamberState chamberState = buildChamberMap(data, 2);
        Map<ChamberState, Integer> costs = new HashMap<>();
        return dfs(chamberState, costs);
    }

    static int part2(List<String> data) {
        List<String> extendedData = new ArrayList<>(data);
        extendedData.add(extendedData.size() - 2, "  #D#C#B#A#");
        extendedData.add(extendedData.size() - 2, "  #D#B#A#C#");
        ChamberState chamberState = buildChamberMap(extendedData, 4);
        Map<ChamberState, Integer> costs = new HashMap<>();
        return dfs(chamberState, costs);
    }

    private static int dfs(ChamberState previousState, Map<ChamberState, Integer> costs) {
        if (costs.containsKey(previousState)) {
            return costs.get(previousState);
        }

        ChamberState state = new ChamberState(previousState);
        int hallwayCost = 0;
        boolean isMoved = true;

        while (isMoved) {
            isMoved = false;

            for (int pos : state.hallway.keySet()) {
                char type = state.hallway.get(pos);
                if (type == '.') {
                    continue;
                }

                int cost = calculateMoveCost(state, type, pos, getHallwayPosition(type));
                if (cost == -1) {
                    continue;
                }

                isMoved = true;
                hallwayCost += cost;

                state.hallway.put(pos, '.');
                state.chambers.get(type).push(type);
            }
        }

        if (state.isCompleted()) {
            costs.put(previousState, hallwayCost);
            return hallwayCost;
        }

        int bestCost = Integer.MAX_VALUE;

        for (char type : previousState.chambers.keySet()) {
            if (state.isOrdered(type)) {
                continue;
            }

            Stack<Character> stack = state.chambers.get(type);

            for (int i : state.hallway.keySet()) {
                int cost = calculateMoveCost(state, stack.peek(), getHallwayPosition(type), i);

                if (cost == -1) {
                    continue;
                }

                ChamberState newState = new ChamberState(state);
                newState.hallway.put(i, newState.chambers.get(type).pop());

                int followingStepsCost = dfs(newState, costs);
                if (followingStepsCost == -1) {
                    continue;
                }

                bestCost = Math.min(bestCost, followingStepsCost + cost + hallwayCost);
            }
        }

        if (bestCost == Integer.MAX_VALUE) {
            return -1;
        }

        costs.put(previousState, bestCost);
        return bestCost;
    }

    private static int calculateMoveCost(ChamberState state, Character type, int start, int end) {
        int current = start;
        int step = Integer.signum(end - start);
        int steps = 0;

        while (current != end) {
            current += step;
            if (state.hallway.getOrDefault(current, '.') != '.') {
                return -1;
            }
            steps++;
        }

        if (start == 2 || start == 4 || start == 6 || start == 8) {
            Stack<Character> stack = state.chambers.get(getChamberPosition(start));

            if (stack.size() == 0 || stack.peek() != type) {
                return -1;
            }

            steps += state.stackSize - stack.size() + 1;
        } else {
            Stack<Character> stack = state.chambers.get(getChamberPosition(end));
            if (stack.size() == state.stackSize) {
                return -1;
            }

            for (Character ch : stack) {
                if (ch != type) {
                    return -1;
                }
            }

            steps += state.stackSize - stack.size();
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

    private static ChamberState buildChamberMap(List<String> data, int stackSize) {
        Map<Integer, Character> hallway = new HashMap<>();
        hallway.put(0, '.');
        hallway.put(1, '.');
        hallway.put(3, '.');
        hallway.put(5, '.');
        hallway.put(7, '.');
        hallway.put(9, '.');
        hallway.put(10, '.');

        Map<Character, Stack<Character>> chambers = new HashMap<>();
        chambers.put('A', new Stack<>());
        chambers.put('B', new Stack<>());
        chambers.put('C', new Stack<>());
        chambers.put('D', new Stack<>());

        for (int i = data.size() - 2; i >= data.size() - stackSize - 1; i--) {
            String line = data.get(i).trim().replaceAll("#", "");
            chambers.get('A').push(line.charAt(0));
            chambers.get('B').push(line.charAt(1));
            chambers.get('C').push(line.charAt(2));
            chambers.get('D').push(line.charAt(3));
        }

        return new ChamberState(hallway, chambers);
    }

    private static class ChamberState {
        private final Map<Integer, Character> hallway;
        private final Map<Character, Stack<Character>> chambers;
        private final int stackSize;

        private ChamberState(Map<Integer, Character> hallway, Map<Character, Stack<Character>> chambers) {
            this.hallway = hallway;
            this.chambers = chambers;
            stackSize = chambers.get('A').size();
        }

        private ChamberState(ChamberState otherChamberState) {
            hallway = new HashMap<>(otherChamberState.hallway);
            chambers = new HashMap<>();
            for (Map.Entry<Character, Stack<Character>> entry : otherChamberState.chambers.entrySet()) {
                @SuppressWarnings("unchecked")
                Stack<Character> copy = (Stack<Character>) entry.getValue().clone();
                chambers.put(entry.getKey(), copy);
            }
            stackSize = otherChamberState.stackSize;
        }

        public boolean isCompleted() {
            return isOrdered('A') && chambers.get('A').size() == stackSize &&
                isOrdered('B') && chambers.get('B').size() == stackSize &&
                isOrdered('C') && chambers.get('C').size() == stackSize &&
                isOrdered('D') && chambers.get('D').size() == stackSize;
        }

        public boolean isOrdered(Character type) {
            Stack<Character> stack = chambers.get(type);

            for (Character character : stack) {
                if (type != character) {
                    return false;
                }
            }

            return true;
        }

        public void print() {
            System.out.printf("\n%s", this);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("#############\n");
            sb.append(String.format("#%c%c.%c.%c.%c.%c%c#\n", hallway.get(0), hallway.get(1), hallway.get(3), hallway.get(5), hallway.get(7), hallway.get(9), hallway.get(10)));

            for (int i = stackSize - 1; i >= 0; i--) {
                char a = chambers.get('A').size() > i ? chambers.get('A').get(i) : '.';
                char b = chambers.get('B').size() > i ? chambers.get('B').get(i) : '.';
                char c = chambers.get('C').size() > i ? chambers.get('C').get(i) : '.';
                char d = chambers.get('D').size() > i ? chambers.get('D').get(i) : '.';
                sb.append(String.format("###%c#%c#%c#%c###\n", a, b, c, d));
            }

            sb.append("#############\n");
            return sb.toString();
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ChamberState chamberState = (ChamberState) o;
            return stackSize == chamberState.stackSize && Objects.equals(hallway, chamberState.hallway) && Objects.equals(chambers, chamberState.chambers);
        }

        @Override
        public int hashCode() {
            return Objects.hash(hallway, chambers, stackSize);
        }
    }
}
