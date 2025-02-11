package cz.sliva.Day7;

public class GoToWork implements Runnable {

    private Day7 dispatcher;
    private long taskResult = 0;
    private int[] taskItems;

    public GoToWork(Day7 dispatcher, String task) {

        this.dispatcher = dispatcher;

        String[] tmp = task.split("[:]");
        taskResult = Long.parseLong(tmp[0]);

        String[] tp = tmp[1].trim().split(" ");
        taskItems = new int[tp.length];

        for (int i = 0; i < tp.length; i++) {
            taskItems[i] = Integer.parseInt(tp[i]);
        }
    }

// 4364915411363 správně !!!

    @Override
    public void run() {
        //writeMessage("Run.");
        Calculation calc = new Calculation(taskResult, 0, taskItems);
        if (calc.getResult()) {
            writeMessage(calc.getStrResult());
            incrementTotal(taskResult);
        } else {

            String s = "";
            for (int i = 0; i < taskItems.length; i++) {
                s += ", " + taskItems[i];
            }
            writeMessage(" ??? : " + s);
        }
    }

    private void writeMessage(String message) {
        synchronized (dispatcher) {
            dispatcher.writeMessage(taskResult, message);
        }
    }

    private void incrementTotal(Long value) {
        synchronized (dispatcher) {
            dispatcher.incrementTotal(value);
        }
    }

}
