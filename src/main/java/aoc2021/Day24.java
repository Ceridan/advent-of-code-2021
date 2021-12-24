package aoc2021;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.LongSupplier;

public class Day24 {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> data = Utils.readInputAsStringArray("day24.txt");

        System.out.printf("Day 24, part 1: %d\n", part1(data));
        System.out.printf("Day 24, part 2: %d\n", part2(data));
    }

    static long part1(List<String> data) {
        Map<Character, List<Integer>> variables = getPatternVariables(data);
        return dfs(variables, 9, 1, 0, 0, 0);
    }

    static long part2(List<String> data) {
        Map<Character, List<Integer>> variables = getPatternVariables(data);
        return dfs(variables, 1, 9, 0, 0, 0);
    }

    private static Map<Character, List<Integer>> getPatternVariables(List<String> data) {
        Map<Character, List<Integer>> variables = new HashMap<>();
        variables.put('x', new ArrayList<>());
        variables.put('y', new ArrayList<>());
        variables.put('z', new ArrayList<>());

        for (int i = 0; i < 14; i++) {
            variables.get('x').add(Integer.parseInt(data.get(i * 18 + 5).split(" ")[2]));
            variables.get('y').add(Integer.parseInt(data.get(i * 18 + 15).split(" ")[2]));
            variables.get('z').add(Integer.parseInt(data.get(i * 18 + 4).split(" ")[2]));
        }

        return variables;
    }

    private static long calculateNextZ(int px, int py, int pz, int digit, long z) {
        if (((z % 26) + px) == digit) {
            return z / pz;
        } else {
            return z / pz * 26 + py + digit;
        }
    }

    private static long dfs(Map<Character, List<Integer>> variables, int firstDigit, int lastDigit, int digitIndex, long prevZ, long number) {
        if (digitIndex == 14) {
            return (prevZ == 0) ? number : -1;
        }

        int px = variables.get('x').get(digitIndex);
        int py = variables.get('y').get(digitIndex);
        int pz = variables.get('z').get(digitIndex);
        int digit = firstDigit;

        if (pz == 1) {
            while (digit >= 1 && digit <= 9) {
                long z = calculateNextZ(px, py, pz, digit, prevZ);
                long res = dfs(variables, firstDigit, lastDigit, digitIndex + 1, z, number * 10 + digit);

                if (res > -1) {
                    return res;
                }

                digit += Math.signum(lastDigit - firstDigit);
            }
        } else {
            while (digit >= 1 && digit <= 9) {
                if (((prevZ % 26) + px) == digit) {
                    long z = calculateNextZ(px, py, pz, digit, prevZ);
                    long res = dfs(variables, firstDigit, lastDigit, digitIndex + 1, z, number * 10 + digit);

                    if (res > -1) {
                        return res;
                    }
                }

                digit += Math.signum(lastDigit - firstDigit);
            }
        }

        return -1;
    }

    // Actually ALU is not used in the solution
    static class ALU {
        private long w;
        private long x;
        private long y;
        private long z;

        public void run(List<String> program, int... args) {
            int nextArgIndex = 0;

            for (String instruction : program) {
                String[] parts = instruction.split(" ");
                char accumulator = parts[1].charAt(0);
                switch (parts[0]) {
                    case "inp": setVar(accumulator, args[nextArgIndex++]); break;
                    case "add": setVar(accumulator, pickVar(accumulator) + parseArg(parts[2])); break;
                    case "mul": setVar(accumulator, pickVar(accumulator) * parseArg(parts[2])); break;
                    case "div": setVar(accumulator, pickVar(accumulator) / parseArg(parts[2])); break;
                    case "mod": setVar(accumulator, pickVar(accumulator) % parseArg(parts[2])); break;
                    case "eql": setVar(accumulator, pickVar(accumulator) == parseArg(parts[2]) ? 1 : 0); break;
                }
            }
        }

        public void reboot() {
            w = 0;
            x = 0;
            y = 0;
            z = 0;
        }

        public long getW() {
            return w;
        }

        public long getX() {
            return x;
        }

        public long getY() {
            return y;
        }

        public long getZ() {
            return z;
        }

        private long pickVar(char variable) {
            switch (variable) {
                case 'w': return w;
                case 'x': return x;
                case 'y': return y;
                case 'z': return z;
                default: throw new IllegalArgumentException();
            }
        }

        private void setVar(char variable, long value) {
            switch (variable) {
                case 'w': w = value; return;
                case 'x': x = value; return;
                case 'y': y = value; return;
                case 'z': z = value; return;
                default: throw new IllegalArgumentException();
            }
        }

        private long parseArg(String arg) {
            char first = arg.charAt(0);
            switch (first) {
                case 'w': return w;
                case 'x': return x;
                case 'y': return y;
                case 'z': return z;
                default: return Long.parseLong(arg);
            }
        }
    }
}
