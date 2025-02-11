package cz.sliva.Day4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Day4 {

    String FILE_PATH = "src/main/java/cz/sliva/Day4/";
    String FILE_NAME = "input.txt";

    private Integer totalSum = 0;

    private char[][] p = new char[140][140];

    @FunctionalInterface
    public interface QuadPredicate<A, B, C, D> {
        boolean test(A a, B b, C c, D d);
    }

    QuadPredicate<Character, Character, Character, Character> XMAS = (a, b, c, d) ->
            a == 'X' && b == 'M' && c == 'A' && d == 'S';
    QuadPredicate<Character, Character, Character, Character> SAMX = (a, b, c, d) ->
            a == 'S' && b == 'A' && c == 'M' && d == 'X';

    public static void main(String[] args) {

        Day4 t = new Day4();
        if (t.loadFiles()) {
            t.runTask();
        }
    }

    public void runTask() {

        System.out.println("\r\nSTART\r\n");

        horizontalne();
        vertikalne();
        diagonalneVpravo();
        diagonalneVlevo();

        System.out.printf("Výsledek je %d.\r\n\r\n", this.totalSum);
    }

    public void horizontalne() {

        for (int y = 0; y < this.p.length; y++) {
            for (int x = 0; x < this.p[y].length - 3; x++) {
                this.totalSum += XMAS.test(p[y][x], p[y][x + 1], p[y][x + 2], p[y][x + 3]) ? 1 : 0;
                this.totalSum += SAMX.test(p[y][x], p[y][x + 1], p[y][x + 2], p[y][x + 3]) ? 1 : 0;
            }
        }
        System.out.printf("Mezisoučet horizontálně: %d.\r\n", this.totalSum);
    }

    public void vertikalne() {

        for (int y = 0; y < this.p.length - 3; y++) {
            for (int x = 0; x < this.p.length; x++) {
                this.totalSum += XMAS.test(p[y][x], p[y + 1][x], p[y + 2][x], p[y + 3][x]) ? 1 : 0;
                this.totalSum += SAMX.test(p[y][x], p[y + 1][x], p[y + 2][x], p[y + 3][x]) ? 1 : 0;
            }
        }
        System.out.printf("Mezisoučet vertikálně: %d.\r\n", this.totalSum);
    }

    public void diagonalneVpravo() {

        for (int y = 3; y < this.p.length; y++) {
            for (int x = 0; x < this.p.length - 3; x++) {
                this.totalSum += XMAS.test(p[y][x], p[y - 1][x + 1], p[y - 2][x + 2], p[y - 3][x + 3]) ? 1 : 0;
                this.totalSum += SAMX.test(p[y][x], p[y - 1][x + 1], p[y - 2][x + 2], p[y - 3][x + 3]) ? 1 : 0;
            }
        }
        System.out.printf("Mezisoučet diagonálně vpravo: %d.\r\n", this.totalSum);
    }

    public void diagonalneVlevo() {

        for (int y = 0; y < this.p.length - 3; y++) {
            for (int x = 0; x < this.p.length - 3; x++) {
                this.totalSum += XMAS.test(p[y][x],p[y + 1][x + 1],p[y + 2][x + 2],p[y + 3][x + 3]) ? 1 : 0;
                this.totalSum += SAMX.test(p[y][x],p[y + 1][x + 1],p[y + 2][x + 2],p[y + 3][x + 3]) ? 1 : 0;
            }
        }
        System.out.printf("Mezisoučet diagonálně vlevo: %d.\r\n", this.totalSum);
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
}



//
//--- Day 4: Ceres Search ---
//        "Looks like the Chief's not here. Next!" One of The Historians pulls out a device and pushes the only button on it. After a brief flash, you recognize the interior of the Ceres monitoring station!
//
//As the search for the Chief continues, a small Elf who lives on the station tugs on your shirt; she'd like to know if you could help her with her word search (your puzzle input). She only has to find one word: XMAS.
//
//This word search allows words to be horizontal, vertical, diagonal, written backwards, or even overlapping other words. It's a little unusual, though, as you don't merely need to find one instance of XMAS - you need to find all of them. Here are a few ways XMAS might appear, where irrelevant characters have been replaced with .:
//
//
//        ..X...
//        .SAMX.
//.A..A.
//        XMAS.S
//        .X....
//The actual word search will be full of letters instead. For example:
//
//        MMMSXXMASM
//        MSAMXMSMSA
//        AMXSXMAAMM
//        MSAMASMSMX
//        XMASAMXAMM
//        XXAMMXXAMA
//        SMSMSASXSS
//        SAXAMASAAA
//        MAMMMXMMMM
//        MXMXAXMASX
//In this word search, XMAS occurs a total of 18 times; here's the same word search again, but where letters not involved in any XMAS have been replaced with .:
//
//        ....XXMAS.
//        .SAMXMS...
//        ...S..A...
//        ..A.A.MS.X
//        XMASAMX.MM
//        X.....XA.A
//        S.S.S.S.SS
//        .A.A.A.A.A
//        ..M.M.M.MM
//        .X.X.XMASX
//Take a look at the little Elf's word search. How many times does XMAS appear?
