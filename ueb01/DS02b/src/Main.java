public class Main {

    public static void main(String[] args) throws Exception{
        Counter c = new Counter();
        CounterRunnable cr = new CounterRunnable(c);
        Thread[] ta = new Thread[20];

        for(int i = 0; i < 20; i++){
            ta[i] = new Thread(cr);
            ta[i].start();
        }

        for(int i = 0; i < 20; i++){
            ta[i].join();
        }
        System.out.println(c.value());
    }


}







/**
 * Hier werden die Threads durch das Nutzen des synchronized-Modifikators dazu gezwungen, dass nur ein Thread zur Zeit eine Methode ausführen kann.
 * Es ist quasi so, als würde man eine Methode nun als "atomare Operation" betrachten.
 *
 * "Both methods lock the same monitor. Therefore, you can't simultaneously execute them on the same object from
 * different threads (one of the two methods will block until the other is finished)"
 *  -Stack Overflow (https://stackoverflow.com/questions/15438727/if-i-synchronized-two-methods-on-the-same-class-can-they-run-simultaneously)
 *  -Da steht eine lange Antwort, die sehr gut ist.
 */