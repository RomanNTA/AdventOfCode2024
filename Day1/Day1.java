package cz.sliva.Day1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

public class Day1 {

    String FILE_PATH = "src/main/java/cz/sliva/Day1/";
    String FILE_NAME = "input.txt";

    private Integer totalSum = 0;
    private final Integer[] p1 = new Integer[1000];
    private final Integer[] p2 = new Integer[1000];

    private Integer position1 = 0;
    private Integer position2 = 0;

    public static void main(String[] args) {

        Day1 t = new Day1();

        if (t.loadFile()) {
            t.calculate();
        };

    }

    private void calculate() {

        Arrays.sort(p1);
        Arrays.sort(p2);

        for (int i = 0; i < p1.length; i++) {

            Integer min = Math.min(p1[i], p2[i]);
            Integer max = Math.max(p1[i], p2[i]);
            totalSum += max - min;
            System.out.printf("%d - %d = %d (%d)\r\n", max, min, max - min, totalSum);

        }
        System.out.printf("\r\nResult: %d ;-)\r\n\r\n", this.totalSum);
    }

    public boolean loadFile() {

        long counter = 0;
        System.out.printf("START %d.\r\n", totalSum);

        Path file = FileSystems.getDefault().getPath(FILE_PATH,FILE_NAME);
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {

            lines.forEach(s -> {
                s = s.replace("   ", " ");
                String[] inp = s.split(" ");
                p1[position1++] = Integer.parseInt(inp[0]);
                p2[position2++] = Integer.parseInt(inp[1]);
            });

        } catch (IOException e) {
            System.out.printf("Error : Load file");
            return false;
        }
        return true;
    }
}