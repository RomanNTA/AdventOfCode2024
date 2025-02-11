package cz.sliva.Day3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day3 {

    String FILE_PATH = "src/main/java/cz/sliva/Day3/";
    String FILE_NAME = "input.txt";

    private Integer totalSum = 0;

    public static void main(String[] args) {

        Day3 t = new Day3();
        t.runTask();
    }

    public void runTask() {

        System.out.printf("START.\r\n\r\n");

        Path file = FileSystems.getDefault().getPath(FILE_PATH,FILE_NAME);
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {

            lines.forEach(s -> {

                String regex = "mul\\(\\d+,\\d+\\)";

                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(s);

                while (matcher.find()) {

                    String[] res = matcher.group().replaceAll("[^0-9,]", "").split(",");
                    Arrays.sort(res);
                    System.out.println("-> " + res[0] + " - " + res[1]);

                    this.totalSum += Integer.parseInt(res[0]) * Integer.parseInt(res[1]);
                }
            });

        } catch (IOException e) {
            System.out.println("Error : Load file.");
            return;
        }
        System.out.printf("Result:  %d.\r\n\r\n\r\n", this.totalSum);
    }
}

//
//--- Day 3: Mull It Over ---
//        "Our computers are having issues, so I have no idea if we have any Chief Historians in stock! You're welcome to check the warehouse, though," says the mildly flustered shopkeeper at the North Pole Toboggan Rental Shop. The Historians head out to take a look.
//
//The shopkeeper turns to you. "Any chance you can see why our computers are having issues again?"
//
//The computer appears to be trying to run a program, but its memory (your puzzle input) is corrupted. All of the instructions have been jumbled up!
//
//It seems like the goal of the program is just to multiply some numbers. It does that with instructions like mul(X,Y), where X and Y are each 1-3 digit numbers. For instance, mul(44,46) multiplies 44 by 46 to get a result of 2024. Similarly, mul(123,4) would multiply 123 by 4.
//
//However, because the program's memory has been corrupted, there are also many invalid characters that should be ignored, even if they look like part of a mul instruction. Sequences like mul(4*, mul(6,9!, ?(12,34), or mul ( 2 , 4 ) do nothing.
//
//        For example, consider the following section of corrupted memory:
//
//xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))
//Only the four highlighted sections are real mul instructions. Adding up the result of each instruction produces 161 (2*4 + 5*5 + 11*8 + 8*5).
//
//Scan the corrupted memory for uncorrupted mul instructions. What do you get if you add up all of the results of the multiplications?

