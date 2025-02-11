package cz.sliva.Day7;

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

public class Day7 {

    String FILE_PATH = "src/main/java/cz/sliva/Day7/";
    String FILE_NAME = "input.txt";

    private List<String> tasks = new ArrayList<>();
    private long totalSum = 0;
    private long totalCount = 0;
    private boolean updateDisplay = false;

    public static void main(String[] args) {

        System.out.println("-".repeat(60));
        System.out.println(" ADVENT OF CODE");
        System.out.println("-".repeat(60));
        System.out.println(" https://adventofcode.com/2024/day/7");
        System.out.println(" Day 7");
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
        boolean show = true;
        if (inp.length() >= 1){
            show = inp.charAt(0) == 'y';
            System.out.println(show ? "Show: yes." : "Show: no.");
        } else {
            System.out.println("Show: no");
        }

        Day7 t = new Day7(show);
        t.createThreads();
    }

    public Day7(boolean updateDisplay) {
        this.updateDisplay = updateDisplay;
    }

    private void createThreads() {

        System.out.println("-".repeat(60));
        if (!loadFiles()) return;

        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        System.out.println( numberOfThreads + "x CPU Threads");
        System.out.println("-".repeat(60));
        System.out.println("Start ...");

        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        int count = 0;
        for (String task: tasks){

            // only for lambda
            final String currentTask = task;

                executor.submit(() -> {
                    //new GoToWork(currentCounter,this, this.getSqare(), xStart, yStart, currentXpos, currentYpos).run();
                    new GoToWork(this, task).run();
                });
            count++;
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            System.out.println("-".repeat(60));
            System.out.println(String.format("Konec: Celkový součet kalibrace %d prvků je = %d. (%d) ", totalCount, totalSum, count));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public boolean loadFiles() {

        Path file = FileSystems.getDefault().getPath(FILE_PATH,FILE_NAME);
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            tasks = lines.toList();
            return true;
        } catch (IOException e) {
            System.out.println("Error : Load file.");
            return false;
        }
    }

    public void writeMessage(long idThread, String message) {
        if (updateDisplay) {
            System.out.println("nr " + idThread + ": " + message);
        }
    }

    public void incrementTotal(long value) {
        this.totalSum += value;
        totalCount++;
    }

}
