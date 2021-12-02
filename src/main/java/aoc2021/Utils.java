package aoc2021;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Utils {
    private final static String BASE_PATH = "src/main/resources/aoc2021/";

    public static List<Integer> ReadInputAsIntegerArray(String filename) throws FileNotFoundException {
        File input = new File(BASE_PATH + filename);
        Scanner scanner = new Scanner(input);

        List<Integer> data = new ArrayList<>();

        while (scanner.hasNextInt()) {
            data.add(scanner.nextInt());
        }

        return data;
    }

    public static List<String> ReadInputAsStringArray(String filename) throws FileNotFoundException {
        File input = new File(BASE_PATH + filename);
        Scanner scanner = new Scanner(input);

        List<String> data = new ArrayList<>();

        while (scanner.hasNextLine()) {
            data.add(scanner.nextLine());
        }

        return data;
    }
}
