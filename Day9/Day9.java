package cz.sliva.Day9;

import java.util.Arrays;

public class Day9 {

    private String inpString = "";
    private int[] buffer;
    private int bufferLength = 0;

    public static void main(String[] args) {
        Day9 t = new Day9();
        t.init();
        t.run();
    }

    public void run() {
        System.out.println("Start.");

        inpString = new Data().inputRaw;
        getLength();

        fileCompacting();
        fileCompacting();

        long count = 0;
        for (int k = 0; k < buffer.length; k++) {
            if (buffer[k] != -1) {
                count += k * buffer[k];
            }
        }
        System.out.println("Filesystem checksum result is " + count);

//        This not working correct
//        Long tmp = Arrays.stream(buffer).filter(x -> x != -1).mapToLong(num -> (long) num).reduce(0, (a, b) -> a + b);
//        System.out.println("Filesystem checksum result is " + tmp);


    }

// 88217064459    - špatně ... too low
// 15527844086096 - špatně ... too high
// 6200294126135  - špatně ... too high
// 6200294120911  - OK !!!


    private void getLength() {

        boolean toggle = true;
        char[] tmp = inpString.toCharArray();
// -------- legth for buffer
        for (int i = 0; i < tmp.length; i++) {
            if (toggle) {
                toggle = !toggle;
                bufferLength += Integer.parseInt(String.valueOf(tmp[i]));
            } else {
                toggle = !toggle;
                bufferLength += Integer.parseInt(String.valueOf(tmp[i]));
            }
        }
// -------- init buffer
        buffer = new int[bufferLength];
        Arrays.fill(buffer, -1);
// -------- load to bufer
        toggle = true;
        int position = 0;
        int counter = 0;
        for (int i = 0; i < tmp.length; i++) {
            if (toggle) {
                toggle = !toggle;
                int value = counter++;
                int q = Integer.parseInt(String.valueOf(tmp[i]));
                while (q-- > 0) {
                    buffer[position++] = value;
                }
            } else {
                toggle = !toggle;
                position += Integer.parseInt(String.valueOf(tmp[i]));
            }
        }
    }

    private void fileCompacting() {

        int i = 0;
        int j = buffer.length - 1;

        while (i < j) {
            while (buffer[i] != -1) {
                i++;
            }
            while (buffer[j] == -1) {
                j--;
            }
            buffer[i] = buffer[j];
            buffer[j] = -1;
            i++;
            j--;
        }
    }

    private static void init() {
        System.out.println("-".repeat(60));
        System.out.println(" ADVENT OF CODE");
        System.out.println("-".repeat(60));
        System.out.println(" https://adventofcode.com/2024/day/9");
        System.out.println(" Day 9, first part");
        System.out.println(" autor: Roman Sliva");
        System.out.println(" portfolio: sliva-roman.cz");
        System.out.println("-".repeat(60));
        System.out.println(" IntelliJ Community Edition 2023.3.3 Java 17 SDK");
        System.out.println("-".repeat(60));
    }

}
