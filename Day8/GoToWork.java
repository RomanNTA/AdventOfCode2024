package cz.sliva.Day8;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class GoToWork implements Runnable {

    BiPredicate<Integer, Integer> testRange = (a, b) -> (a >= this.xMax || a < 0 || b >= this.yMax || b < 0);
    private List<Integer> buffer = new ArrayList<>();
    private Day8 dispatcher;
    private char[][] square;

    private int xMax;
    private int yMax;
    private int x1 = 0;
    private int x2 = 0;
    private int px1 = 0;
    private int px2 = 0;

    private int y1 = 0;
    private int y2 = 0;
    private int py1 = 0;
    private int py2 = 0;

    private char aChar;

    public GoToWork(Day8 dispatcher, char[][] square, char aChar) {

        this.dispatcher = dispatcher;
        this.square = square;
        this.xMax = square[0].length;
        this.yMax = square.length;
        this.aChar = aChar;
    }

    @Override
    public void run() {
        boolean start = true;
        while (findNextChar(start)) {
            start = false;

            // posun 1 do 2 pozice
            x1 = x2;
            y1 = y2;
            px1 = py1 = px2 = py2 = 0;

            // hledej další
            while (findNextChar(start)) {


                if (x1 > x2 && x1 != x2) {
                    px1 = x1 - x2;
                    px2 = x2 - x1;
                } else {
                    px1 = x1 - x2;
                    px2 = x2 - x1;
                }

                if (y1 < y2 && y1 != y2) {
                    py1 = y1 - y2;
                    py2 = y2 - y1;
                }

                writeBuffer(x1 + px1, y1 + py1);
                writeBuffer(x2 + px2, y2 + py2);

                // reset offset
                px1 = px2 = py1 = py2 = 0;
            }
            // move to the next position
            x2 = x1;
            y2 = y1;
        }
        //showArray();
        sendUniqueCode();
    }

    private boolean setNextPosition() {
        x2 = x2 + 1 < xMax ? x2 + 1 : 0;
        if (x2 == 0) {
            y2 = y2 + 1 < yMax ? y2 + 1 : -1;
        }
        return y2 == -1;
    }

    private boolean findNextChar(boolean start) {

        if (start && square[y2][x2] == aChar) {
            return true;
        } else {
            if (setNextPosition()) {
                return false;
            }
            while (square[y2][x2] != aChar) {
                if (setNextPosition()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void writeBuffer(int valueX, int valueY){
        if (!testRange.test(valueX, valueY)) {

            // Always save position
            buffer.add((valueY) * xMax + (valueX));

            // You don't always have to save the tag.
            if (square[valueY][valueX] == '.') {
                square[valueY][valueX] = '#';
            }
        }
    }

    private void sendUniqueCode() {
        for (Integer c : buffer) {
            dispatcher.uniqueLocations.put(c, dispatcher.uniqueLocations.getOrDefault(c, 0) + 1);
        }
    }

//    need :) ... only for testing
//    private void showArray() {
//        synchronized (dispatcher) {
//            System.out.println("-".repeat(50));
//            System.out.print("    ");
//            for (int j = 0; j < square[0].length; j++) {
//                System.out.printf("%3d", j);
//            }
//            System.out.println();
//            System.out.println("-".repeat(50));
//            for (int i = 0; i < square.length; i++) {
//                System.out.printf(" %2d | ", i);
//                for (int j = 0; j < square[i].length; j++) {
//                    System.out.print((char) square[i][j] + "  ");
//                }
//                System.out.println();
//            }
//            System.out.println("-".repeat(50));
//        }
//    }

}
