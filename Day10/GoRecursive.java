package cz.sliva.Day10;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Builder
public class GoRecursive {

    @FunctionalInterface
    public interface TriPredicate<A, B, C> {
        boolean test(A a, B b, C c);
    }

    private List<Point> listOfEndPoint = new ArrayList<>();
    private Map<String, Integer> listOfNine = new HashMap<>();
    private Day10 dispatcher;
    private Point zero;
    private int[][] square;
    private int xMax = 0;
    private int yMax = 0;


    public GoRecursive(Day10 dispatcher, Point zero, int[][] square) {
        this.dispatcher = dispatcher;
        this.zero = zero;
        this.square = square;
        getListsOfPoint();
        this.yMax = this.square.length;
        this.xMax = this.square[0].length;
    }

    public void run() {

        int x = (int) zero.getX();
        int y = (int) zero.getY();
        cyklus(square[y][x], x, y);

        synchronized (dispatcher) {
            dispatcher.writeMessage(x + "-" + y, "Celkem: " + listOfNine.size());
            dispatcher.incrementTotal(listOfNine.size());
        }
    }

    TriPredicate<Integer,Integer,Integer> fUp = (x, y, value) ->
        (y - 1 >= 0) && (square[y - 1][x] == value);

    TriPredicate<Integer,Integer,Integer> fDown = (x, y, value) ->
            (y + 1 < yMax) && (square[y + 1][x] == value);

    TriPredicate<Integer,Integer,Integer> fLeft = (x, y, value) ->
            (x - 1 >=0) && (square[y][x-1] == value);

    TriPredicate<Integer,Integer,Integer> fRight = (x, y, value) ->
            (x + 1 <xMax) && (square[y][x+1] == value);

    private void cyklus(int value, int x, int y) {
        if (value == 9) {
            listOfNine.put(x + "-" + y, listOfNine.getOrDefault(x + "-" + y, 0) + 1);
            return;
        }
        value++;

        if (fUp.test(x, y,value)) {
            cyklus(value, x, y - 1);
        }
        if (fDown.test(x, y,value)) {
            cyklus(value, x, y + 1);
        }
        if (fRight.test(x, y,value)) {
            cyklus(value, x + 1, y);
        }
        if (fLeft.test(x, y,value)) {
            cyklus(value, x - 1, y);
        }
    }

    private void getListsOfPoint() {
        for (int y = 0; y < square.length; y++) {
            for (int x = 0; x < square[y].length; x++) {
                if (square[y][x] == 9) {
                    listOfEndPoint.add(new Point(x, y));
                }
            }
        }
    }


}
