package aoc2021;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Day02 {
    public static void main(String[] args) throws FileNotFoundException {
        List<String> data = Utils.readInputAsStringArray("day02.txt");

        System.out.printf("Day 02, part 1: %d\n", part1(data));
        System.out.printf("Day 02, part 2: %d\n", part2(data));
    }

    static long part1(List<String> data) {
        List<Command> commands = buildCommands(data);
        long horizontal = 0;
        long depth = 0;

        for (Command command : commands) {
            switch (command.getCommand()) {
                case "forward":
                    horizontal += command.getValue();
                    break;
                case "up":
                    depth -= command.getValue();
                    break;
                case "down":
                    depth += command.getValue();
            }
        }

        return horizontal * depth;
    }

    static long part2(List<String> data) {
        List<Command> commands = buildCommands(data);
        long horizontal = 0;
        long depth = 0;
        long aim = 0;

        for (Command command : commands) {
            switch (command.getCommand()) {
                case "forward":
                    horizontal += command.getValue();
                    depth += aim * command.getValue();
                    break;
                case "up":
                    aim -= command.getValue();
                    break;
                case "down":
                    aim += command.getValue();
            }
        }

        return horizontal * depth;
    }

    private static List<Command> buildCommands(List<String> input) {
        List<Command> commands = new ArrayList<>(input.size());

        for (String line : input) {
            String[] splitted = line.split(" ");
            String command = splitted[0];
            int value = Integer.parseInt(splitted[1]);
            commands.add(new Command(command, value));
        }

        return commands;
    }

    private static class Command {
        private final String command;
        private final int value;

        public Command(String command, int value) {
            this.command = command;
            this.value = value;
        }

        public String getCommand() {
            return command;
        }

        public int getValue() {
            return value;
        }
    }
}
