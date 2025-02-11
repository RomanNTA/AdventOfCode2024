package cz.sliva.Day7;

import lombok.Getter;

import java.util.function.BiFunction;

//@Builder
public class Calculation {

    BiFunction<Long, Long, Long> funcAdd = (a, b) -> a + b;
    BiFunction<Long, Long, Long> funcMul = (a, b) -> a * b;

    private Long result;
    private Long item;
    private Long runningTotal;

    @Getter
    private String strResult = "";

    private int[] nextItems = new int[0];

    public Calculation(long result, long runningTotal, int[] nextItems) {
        this.result = result;
        this.runningTotal = runningTotal;
        this.item = (long) nextItems[0];

        if (nextItems.length > 1) {
            this.nextItems = new int[nextItems.length - 1];
            for (int i = 1; i < nextItems.length; i++) {
                this.nextItems[i - 1] = nextItems[i];
            }
        }
    }

    public Boolean getResult() {

        String prefix = runningTotal == 0 ? " = " : " + ";
        if (worker(funcAdd, prefix)) {
            return true;
        } else {
            prefix = runningTotal == 0 ? " = " : " * ";
            runningTotal = runningTotal == 0 ? 1 : runningTotal;
            return worker(funcMul, prefix);
        }
    }

    private boolean worker(BiFunction<Long, Long, Long> operation, String prefix) {

        if (operation.apply(runningTotal, item).equals(result)) {
            strResult = prefix + item;
            return true;
        } else if (operation.apply(runningTotal, item) < result) {
            if (nextItems.length > 0) {
                Calculation calc = new Calculation(result, operation.apply(runningTotal, item), nextItems);
                if (calc.getResult()) {
                    strResult = prefix + item + calc.getStrResult();
                    return true;
                }
            }
        }
        return false;
    }


}
