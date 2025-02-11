package cz.sliva.Day8;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Day8 {

    String FILE_PATH = "src/main/java/cz/sliva/Day8/";
    String FILE_NAME = "input.txt";

    private static char[][] orig;
    private Map<Character, Integer> antinodes = new HashMap<>();
    public Map<Integer, Integer> uniqueLocations = new ConcurrentHashMap<>();
    private int xMax = 0;
    private int yMax = 0;
    private int xStart = 0;
    private int yStart = 0;

    public static void main(String[] args) {
        Day8 t = new Day8();
        t.init();
        if (t.loadFiles()) {
            System.out.println("Start.");
            t.findAllAntinodes();
            t.createThreads();
        }
    }

    private void createThreads() {

        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        System.out.println(numberOfThreads + "x CPU Threads");

        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        try {
            antinodes.forEach((nodeChar, count) -> {
                executor.submit(() -> {
                    new GoToWork(this, this.getSquare(),nodeChar).run();
                });
            });
            executor.shutdown();
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                System.out.println(" Počet unikátních kódu je " + uniqueLocations.size() + "x ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public char[][] getSquare() {
        // problem with clone() ... then
        char[][] result = new char[yMax][xMax];
        for (int i = 0; i < orig.length; i++) {
            for (int j = 0; j < orig[i].length; j++) {
                result[i][j] = ((char) orig[i][j]);
            }
        }
        return result;
    }

    public boolean loadFiles() {

        if (orig == null) {

            List<String> tmp = new ArrayList<>();

            Path file = FileSystems.getDefault().getPath(FILE_PATH, FILE_NAME);
            try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
                tmp = lines.toList();
            } catch (IOException e) {
                System.out.println("Error : Load file.");
                return false;
            }
            yMax = tmp.size();
            xMax = tmp.get(0).length();
            orig = new char[yMax][xMax];

            int i = 0;
            for (String s : tmp) {
                orig[i++] = s.toCharArray();
            }
        }
        return true;
    }

    public void writeMessage(int idThread, String message) {
        System.out.println(message);
    }

    private void findAllAntinodes() {
        for (yStart = 0; yStart < orig.length; yStart++) {
            for (xStart = 0; xStart < orig[yStart].length; xStart++) {
                Character ch = orig[yStart][xStart];
                if (ch != '.') {
                    antinodes.put(ch, antinodes.getOrDefault(ch, 0) + 1);
                }
            }
        }
    }

    private static void init() {
        System.out.println("-".repeat(60));
        System.out.println(" ADVENT OF CODE");
        System.out.println("-".repeat(60));
        System.out.println(" https://adventofcode.com/2024/day/8");
        System.out.println(" Day 8, second part");
        System.out.println(" autor: Roman Sliva");
        System.out.println(" portfolio: sliva-roman.cz");
        System.out.println("-".repeat(60));
        System.out.println(" IntelliJ Community Edition 2023.3.3 Java 17 SDK");
        System.out.println("-".repeat(60));
    }

}
