package cz.sliva.Day1b;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day1b {

    String FILE_PATH = "src/main/java/cz/sliva/Day1b/";
    String FILE_NAME = "input.txt";

    private Integer totalSum = 0;
    List<String> rows = new ArrayList<>();

    public static void main(String[] args) {
        (new Day1b()).run();
    }

// 391 je špatně
// 10540 je špatně
// 22539317 OK !!!

    private void run() {

        if (!loadFile()) {
            return;
        }

        List<Integer> a1 = new ArrayList<>();
        List<Integer> a2 = new ArrayList<>();

        rows.forEach(s -> {
            s = s.replace("   ", " ");
            String[] inp = s.split(" ");
            a1.add(Integer.parseInt(inp[0]));
            a2.add(Integer.parseInt(inp[1]));
        });

        Map<Integer, Integer> b1 = new HashMap<>();
        Map<Integer, Integer> b2 = new HashMap<>();

        a2.forEach((a) -> b2.put(a, b2.getOrDefault(a, 0) + 1));

        for (Integer key : a1) {
            if (b2.containsKey(key)) {
                totalSum += key * b2.get(key);
            }
        }
        System.out.printf("\r\nResult: %d ;-)\r\n\r\n", this.totalSum);
    }

    public boolean loadFile() {

        Path file = FileSystems.getDefault().getPath(FILE_PATH, FILE_NAME);
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            rows = lines.toList();
        } catch (IOException e) {
            System.out.println("Error : Load file.");
            return false;
        }
        return true;
    }

}