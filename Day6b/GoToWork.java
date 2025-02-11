package cz.sliva.Day6b;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
public class GoToWork implements Runnable {

    private int idThread;
    BiPredicate<Integer, Integer> testRange = (a, b) -> (a >= this.xMax || a < 0 || b >= this.yMax || b < 0);
    private List<Integer> buffer = new ArrayList<>();
    private Day6B dispatcher;
    private char[][] square;
    // Start position
    private int xMax;
    private int yMax;
    // Orientation
    private int orientationY = 0;
    private int orientationX = 0;

    private Integer x;
    private Integer y;

    public GoToWork(int idThread, Day6B dispatcher, char[][] square,
                    int xStart, int yStart, int xpos, int ypos) {

        this.idThread = idThread;
        this.dispatcher = dispatcher;
        this.square = square;
        this.x = xStart;
        this.y = yStart;
        this.square[ypos][xpos] = '#';
        this.xMax = square[0].length;
        this.yMax = square.length;
        this.orientationY = -1;
    }

    @Override
    public void run() {
        //writeMessage("Run.");
        if (cyklus()) {
            incrementTotal();
        }
    }

    private boolean cyklus(){

        while (true) {
            while (square[y][x] != '#') {
                square[y][x] = 'X';
                x += orientationX;
                y += orientationY;
                if (testRange.test(x, y)) {
                    return false;
                }
            }
            x -= orientationX;
            y -= orientationY;
            changeOrientation();
            buffer.add(x + y * xMax );

            // až uděláš 500 a víc otoček, spustíme hledání
            if (buffer.size() > 500) {
                if (testSmycky()) {
                    return true;
                }
            }
            x += orientationX;
            y += orientationY;
            if (testRange.test(x, y)) {
                return false;
            }
        }
    }
    private boolean testSmycky() {
        List<Integer> result = new ArrayList<>();
        int n = buffer.size();
        for (int length = 2; length <= n / 2; length++) {
            for (int start = 0; start <= n - length * 2; start++) {
                List<Integer> sequence = buffer.subList(start, start + length);

                for (int next = start + length; next <= n - length; next++) {
                    if (buffer.subList(next, next + length).equals(sequence)) {
                        if (sequence.size() > result.size()) {
                            result = new ArrayList<>(sequence);
                        }
                    }
                }
            }
        }

        if (result.size() > 0) {
            String s = result.stream().map(String::valueOf).collect(Collectors.joining(", "));
            writeMessage("Find ... :) = " + s);
            return true;
        } else {
            return false;
        }
    }

    private void changeOrientation() {

        if (orientationY > 0) {
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
    }

    private void writeMessage(String message){
        synchronized (dispatcher){
            dispatcher.writeMessage(idThread, message);
        }
    }
    private void incrementTotal(){
        synchronized (dispatcher){
            dispatcher.incrementTotal();
        }
    }

}
