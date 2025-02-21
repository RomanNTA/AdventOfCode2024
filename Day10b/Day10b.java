package cz.sliva.Day10b;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Day10b {

    boolean DEBUG = false;
    String FILE_PATH = "src/main/java/cz/sliva/Day10b/";
    String FILE_NAME = "input.txt";
    private int[][] orig;
    private long totalSum = 0;
    private List<Point> listOfStartPoint = new ArrayList<>();
    private int xMax = 0;
    private int yMax = 0;


    public static void main(String[] args) {
        (new Day10b()).run();
    }

//  1116 - OK !!! ;-))))


    private void run() {

        init();
        if (loadFiles()) {
            System.out.println("Start.");
            getListsOfPoint();
            // -------------------------------------------------------------------------------------
            if (DEBUG) {

                System.out.println("Start poz. - 0");
                for (Point p : listOfStartPoint) {
                    System.out.printf("%3d %3d\r\n", ((int) p.getX()), ((int) p.getY()));
                }
                System.out.println();
            }
            // -------------------------------------------------------------------------------------
            createThreads();
        }
    }

    private void createThreads() {

        System.out.println("-".repeat(60));
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        System.out.println(numberOfThreads + "x CPU Threads");
        System.out.println("-".repeat(60));
        System.out.println("Start threads.");

        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        for (Point zero : listOfStartPoint) {
            // only for lambda
            final Point point = zero;
            executor.submit(() -> {
                new GoRecursive(this, point, this.getSquare(point)).run();
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            System.out.println("-".repeat(60));
            System.out.println(String.format("Konec: Celkový součet stezek na vrcholy je %d.\r\n\r\n", totalSum));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int[][] getSquare(Point p) {
        int[][] result = new int[yMax][xMax];
        for (int i = 0; i < orig.length; i++) {
            result[i] = (int[]) orig[i].clone();
        }
        return result;
    }

    private void getListsOfPoint() {
        for (int y = 0; y < orig.length; y++) {
            for (int x = 0; x < orig[y].length; x++) {
                if (orig[y][x] == 0) {
                    listOfStartPoint.add(new Point(x, y));
                }
            }
        }
    }

    private static void init() {
        System.out.println("-".repeat(60));
        System.out.println(" ADVENT OF CODE");
        System.out.println("-".repeat(60));
        System.out.println(" https://adventofcode.com/2024/day/10");
        System.out.println(" Day 10");
        System.out.println(" autor: Roman Sliva");
        System.out.println(" portfolio: sliva-roman.cz");
        System.out.println("-".repeat(60));
        System.out.println(" IntelliJ Community Edition 2023.3.3 Java 17 SDK");
        System.out.println("-".repeat(60));
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

            if (DEBUG) {
                tmp.forEach(System.out::println);
            }

            yMax = tmp.size();
            xMax = tmp.get(0).length();
            orig = new int[yMax][xMax];

            int i = 0;
            for (String s : tmp) {
                orig[i++] = s.chars().map(Character::getNumericValue).toArray();
            }
        }
        return true;
    }

    public void incrementTotal(long value) {
        this.totalSum += value;
    }


}
