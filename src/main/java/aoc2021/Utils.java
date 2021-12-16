package aoc2021;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Utils {
    private final static String BASE_PATH = "src/main/resources/aoc2021/";

    public static List<Integer> readInputAsIntegerArray(String filename) throws FileNotFoundException {
        File input = getInputFile(filename);
        Scanner scanner = new Scanner(input);

        List<Integer> data = new ArrayList<>();

        while (scanner.hasNextInt()) {
            data.add(scanner.nextInt());
        }

        return data;
    }

    public static List<String> readInputAsStringArray(String filename) throws FileNotFoundException {
        File input = getInputFile(filename);
        Scanner scanner = new Scanner(input);

        List<String> data = new ArrayList<>();

        while (scanner.hasNextLine()) {
            data.add(scanner.nextLine());
        }

        return data;
    }

    public static List<Integer> readCommaSeparatedInputAsIntegerArray(String filename) throws FileNotFoundException {
        File input = getInputFile(filename);
        Scanner scanner = new Scanner(input);
        String line = scanner.nextLine();

        return Arrays.stream(line.split(","))
            .map(Integer::parseInt)
            .collect(Collectors.toList());
    }

    public static String readInputAsString(String filename) throws FileNotFoundException {
        File input = getInputFile(filename);
        Scanner scanner = new Scanner(input);

        return scanner.nextLine();
    }

    public static File getInputFile(String filename) {
        return new File(BASE_PATH + filename);
    }
}
