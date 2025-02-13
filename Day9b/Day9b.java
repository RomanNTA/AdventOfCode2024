package cz.sliva.Day9b;

import java.util.Arrays;

public class Day9b {

    boolean DEBUG = false;
    private String inpString = "";

    private int[] buffer;
    private int bufferLength = 0;

    private int blockValue = 0;
    private int blockLength = 0;
    private int positionStartOfBlock = 0;
    private int positionEndOfBlock = 0;


    public static void main(String[] args) {
        (new Day9b()).run();
    }

//  6227018762944 - špatně ... too high
//  6227018762750 - OK !!! ;-))))


    private void run() {

        System.out.println("Start.");

        if (DEBUG) {
            inpString = new Data().inputTest;
        }
        else {
            inpString = new Data().inputRaw;
        }

        bufferPreparation();

        int i = 0;
        int j = 0;
        int first = 0;
        positionStartOfBlock = buffer.length;
        showBuffer();
        while (first <= positionStartOfBlock) {

            // nastavený poslední blok v proměnných
            nextBlock();

            // Najdi první pozici a
            boolean result;
            do {
                result = correctBlock(first, blockLength);
                if (!result) {
                    first = firstPos(first+1);
                }
            } while (!result && first <= positionStartOfBlock);

            if (result) {
                writeChange(first);
            }
            showBuffer();
            first = firstPos(0);
        }

        long count = 0;
        for (int k = 0; k < buffer.length; k++) {
            if (buffer[k] != -1) {
                count += k * buffer[k];
            }
        }
        System.out.println("Kontrolní součet je " + count);

    }


    private void writeChange(int position) {
        if (blockLength == 1) {
            buffer[position] = buffer[positionStartOfBlock];
            buffer[positionStartOfBlock] = -1;
        } else {
            System.arraycopy(buffer, positionStartOfBlock, buffer, position, blockLength);
            Arrays.fill(buffer, positionStartOfBlock, positionEndOfBlock + 1, -1);
        }
    }


    private void nextBlock() {

        positionEndOfBlock = positionStartOfBlock - 1;

        while (buffer[positionEndOfBlock] == -1) {
            positionEndOfBlock--;
        }
        blockValue = buffer[positionEndOfBlock];

        positionStartOfBlock = positionEndOfBlock;

        while (buffer[positionStartOfBlock] == blockValue) {
            positionStartOfBlock--;
        }
        positionStartOfBlock++;
        blockLength = positionEndOfBlock - positionStartOfBlock + 1;
    }


    private boolean correctBlock(int position, int length) {
        for (int i = position; i < position + length; i++) {
            if (i > positionStartOfBlock) {
                return false;
            }
            if (buffer[i] != -1) {
                return false;
            }
        }
        return true;
    }

    private int firstPos(int fromPosition) {

        // najdi první -1
        while (buffer[fromPosition] != -1) {
            fromPosition++;
            if (fromPosition > positionStartOfBlock) {
                return bufferLength;
            }
        }
        return fromPosition;
    }





    // ---------------- hotové ... máme plný buffer !!!
    private void bufferPreparation() {

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
        showBuffer();
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
        showBuffer();
    }
// ---------------- hotové ... máme plný buffer !!!

    private static void init() {
        System.out.println("-".repeat(60));
        System.out.println(" ADVENT OF CODE");
        System.out.println("-".repeat(60));
        System.out.println(" https://adventofcode.com/2024/day/8");
        System.out.println(" Day 9, second part");
        System.out.println(" autor: Roman Sliva");
        System.out.println(" portfolio: sliva-roman.cz");
        System.out.println("-".repeat(60));
        System.out.println(" IntelliJ Community Edition 2023.3.3 Java 17 SDK");
        System.out.println("-".repeat(60));
    }


    private void showBuffer() {
        if (!DEBUG) return;
        for (int k = 0; k < buffer.length; k++) {
            System.out.printf("%3s", (buffer[k] == -1) ? "." : buffer[k]);
        }
        System.out.println();
    }


}
