package aoc2021;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {
    public static List<Integer> ReadInputAsIntegerArray(String filename) throws FileNotFoundException {
        File input = new File("src/main/resources/aoc2021/" + filename);
        Scanner scanner = new Scanner(input);

        List<Integer> data = new ArrayList<>();

        while (scanner.hasNextInt()) {
            data.add(scanner.nextInt());
        }

        return data;
    }
}
