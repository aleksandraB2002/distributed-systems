public class Main {

    public static void main(String[] args) throws Exception{
        Counter counter = new Counter();


        Thread incrementThread1 = new Thread() {
            public void run(){
                for (int i = 0; i < 1000; i++) {
                    counter.increment();
                }
            }
        };

        Thread incrementThread2 = new Thread(){
            public void run(){
                for (int i = 0; i < 1000; i++) {
                    counter.decrement();
                }
            }
        };

        incrementThread1.start();
        incrementThread2.start();

        incrementThread1.join();
        incrementThread2.join();


        System.out.println(counter.value());

    }


}

/**
 * Durch das Ergänzen des synchronized an den Funktionen der Klasse Counter wird verhindert, dass die Threads nicht
 * gleichzeitig auf die Variable c des Counters zugreifen. So können beim Auslesen und Ändern des Werts keine Interferenzen
 * und so falsche Programmabläufe entstehen.
 */