package cz.sliva.Day2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day2 {

    String FILE_PATH = "src/main/java/cz/sliva/Day2/";
    String FILE_NAME = "input.txt";

    private Integer totalSum = 0;
    private final Integer[] p1 = new Integer[1000];
    private final Integer[] p2 = new Integer[1000];

    private Integer position1 = 0;
    private Integer position2 = 0;

    public static void main(String[] args) {

        Day2 t = new Day2();

        if (t.loadFile()) {
            t.calculate();
        }
    }

    private void calculate() {

        List<Integer> a1 = Arrays.stream(p1).toList();
        List<Integer> a2 = Arrays.stream(p2).toList();

        Map<Integer,Integer> b1 = new HashMap<>(1000);
        Map<Integer,Integer> b2 = new HashMap<>(1000);

        a1.forEach((a) -> b1.put(a, b1.getOrDefault(a, 0) + 1));
        a2.forEach((a) -> b2.put(a, b2.getOrDefault(a, 0) + 1));

        b1.forEach((a,b)-> {

            if (b2.containsKey(a)) {
                int key = b2.get(a);
                int value = b2.get(a);

                System.out.printf(" a = %d, b= %d, c = %d, d = %d\r\n", a,b, key, value);
                totalSum += b * key;
            }
        });
        System.out.printf("Result = %d\r\n\r\n", totalSum);
    }

    public boolean loadFile() {

        System.out.println("\r\nSTART\r\n");

        Path file = FileSystems.getDefault().getPath(FILE_PATH,FILE_NAME);
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {

            lines.forEach(s -> {
                s = s.replace("   ", " ");
                String[] inp = s.split(" ");
                p1[position1++] = Integer.parseInt(inp[0]);
                p2[position2++] = Integer.parseInt(inp[1]);
            });

        } catch (IOException e) {
            System.out.println("Error : Load file.");
            return false;
        }
        return true;
    }
}