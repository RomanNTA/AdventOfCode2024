package cz.sliva.Day6b;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Day6B {


    String FILE_PATH = "src/main/java/cz/sliva/Day6b/";
    String FILE_NAME = "input.txt";

    private static char[][] orig;
    private Integer totalSum = 0;
    private int xMax = 0;
    private int yMax = 0;
    private int xStart = 0;
    private int yStart = 0;

    public boolean isUpdateDisplay() {
        return updateDisplay;
    }

    private boolean updateDisplay = false;


    public static void main(String[] args) {

        Day6B t = new Day6B(init());
        if (t.loadFiles()) {
            System.out.println("Start.");
            t.findStartPosition();
            t.createThreads();
        }
    }

    public Day6B(boolean updateDisplay) {
        this.updateDisplay = updateDisplay;
    }

    private void createThreads() {

        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        System.out.println(numberOfThreads + "x CPU Threads");

        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        int counter = 0;
        for (int ypos = 0; ypos < yMax; ypos++) {
            for (int xpos = 0; xpos < xMax; xpos++) {

                // only for lambda
                final int currentYpos = ypos;
                final int currentXpos = xpos;
                final int currentCounter = counter;

                if (orig[ypos][xpos] != '#') {
                    executor.submit(() -> {
                        new GoToWork(currentCounter, this, this.getSquare(), xStart, yStart, currentXpos, currentYpos).run();
                    });
                }
                counter++;
            }
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            System.out.println(String.format("End of game: I found %d infinite loops. =  ", this.totalSum));
        } catch (InterruptedException e) {
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
        if (updateDisplay) {
            System.out.println("id " + idThread + ": " + message);
        }
    }

    public void incrementTotal() {
        this.totalSum++;
    }

    private void findStartPosition() {
        for (yStart = 0; yStart < orig.length; yStart++) {
            for (xStart = 0; xStart < orig[yStart].length; xStart++) {
                if (orig[yStart][xStart] == '^') {
                    return;
                }
            }
        }
    }

    private static boolean init() {
        System.out.println("-".repeat(60));
        System.out.println(" ADVENT OF CODE");
        System.out.println("-".repeat(60));
        System.out.println(" https://adventofcode.com/2024/day/6");
        System.out.println(" Day 6, second part");
        System.out.println(" autor: Roman Sliva");
        System.out.println(" portfolio: sliva-roman.cz");
        System.out.println("-".repeat(60));
        System.out.println(" IntelliJ Community Edition 2023.3.3 Java 17 SDK");
        System.out.println("-".repeat(60));

        Scanner console = new Scanner(System.in, "UTF-8");

        System.out.println(" Do you want to display the found strings ? ");
        System.out.println(" [Y,N]");

        console.reset();
        try {
            while (System.in.available() > 0) {
                System.in.read();
            }
        } catch (IOException e) {
            System.out.print("Chyba: Není přístupná/otevřená konzole.");
        }

        String inp = console.nextLine().toLowerCase(Locale.ROOT).trim();
        boolean show = false;
        if (inp.length() >= 1) {
            show = inp.charAt(0) == 'y';
            System.out.println(show ? "Show: yes." : "Show: no.");
        } else {
            System.out.println("Show: no.e");
        }
        return show;
    }

}
