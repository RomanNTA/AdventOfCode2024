package cz.sliva.Day6a;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiPredicate;
import java.util.stream.Stream;


public class Day6a {

    Boolean FORWARD = true;
    Boolean BACKWARDS = false;

    String FILE_PATH = "src/main/java/cz/sliva/Day6a/";
    String FILE_NAME = "input.txt";

    BiPredicate<Integer, Integer> testRange = (a, b) -> (a > 129 || a < 0 || b > 129 || b < 0);

    private char[][] p = new char[130][130];
    public Integer totalSum = 0;

    private int x = 0;
    private int y = 0;

    private int orientationY = 0;
    private int orientationX = 0;

    public static void main(String[] args) {

        Day6a t = new Day6a();
        if (t.loadFiles()) {
            System.out.println("START");
            t.searchStartPosition();
            t.runTask();
            t.calculate();
            System.out.println(String.format("Celkem je X = %dx ", t.totalSum));
        }
    }

    public void runTask() {

        orientationY = -1;
        while (true) {
            while (p[y][x] != '#') {
                p[y][x] = 'X';
                go(FORWARD);
                System.out.println(String.format("x = %d, y = %d", x, y));
                if (testRange.test(x, y)) {
                    return;
                }
            }
            go(BACKWARDS);
            changeOrientation();
            go(FORWARD);
            System.out.println(String.format("x = %d, y = %d", x, y));
            if (testRange.test(x, y)) {
                return;
            }
        }
    }

    public void calculate (){
        for (int y = 0; y < p.length; y++) {
            for (int x = 0; x < p[y].length; x++) {
                if (p[y][x] == 'X') {
                    this.totalSum++;
                }
            }
        }
    }

    private void go( boolean goInFront){
        if (goInFront) {
            x += orientationX;
            y += orientationY;
        } else {
            x -= orientationX;
            y -= orientationY;
        }
    };

    private void changeOrientation() {

        if (this.orientationY > 0) {
            orientationY = 0;
            orientationX = -1;
        } else if (orientationY < 0) {
            orientationY = 0;
            orientationX = 1;
        } else if (orientationX > 0) {
            orientationY = 1;
            orientationX = 0;
        } else if (orientationX < 0) {
            orientationY = -1;
            orientationX = 0;
        }
        System.out.println(String.format("ox= %d, oy= %d", orientationX, orientationY));
    }

    public boolean loadFiles() {

        Path file = FileSystems.getDefault().getPath(FILE_PATH, FILE_NAME);
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            int i = 0;
            for (String s : lines.toList()) {
                this.p[i++] = s.toCharArray();
            }
        } catch (IOException e) {
            System.out.println("Error : Load file.");
            return false;
        }
        return true;
    }

    public void searchStartPosition() {

        for (y = 0; y < p.length; y++) {
            for (x = 0; x < p[y].length; x++) {
                if (p[y][x] == '^') {
                    System.out.printf("\r\nStart position y = %d, x = %d.\r\n", y, x);
                    return;
                }
            }
        }
    }

}
