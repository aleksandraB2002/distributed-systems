public class CounterRunnable implements Runnable {

    private Counter counter;

    CounterRunnable(Counter pCounter) {
        this.counter = pCounter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            counter.decrement();
            counter.increment();
        }
    }
}
