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
 * In diesem Beispiel werden die beiden Threads incrementThread und decrementThread genutzt. Auch hier werden die Threads
 * wie in Aufgabe 1 ausgeführt. Das bedeutet, dass die Threads sich in ihrer Ausführung erneut gegenseitig unterbrechen
 * (durch Verteilung der CPU-Ressourcen). Da die Threads auf die gemeinsame Variable c des Counters durch counter.value
 * zugreifen, kommt es dazu, dass wenn die Prozesse sich zwischen Auslesen und aktualisieren des Wertes c abwechseln,
 * dass für den jeweiligen Schritt ein veralteter Wert von c genutzt wird.
 * So kommt es dazu, dass der finale Wert der Ausgabe nur äußerst selten auf den eigentlich korrekten Wert liegt (hier 0).
 * Wenn nun das Wort "Synchronized" zu den Funktionen der Klasse Counter ergänzt wird, wird diese Interferenz verhindert.
 * Dies liegt daran, dass ein Thread nur auf eine gemeinsame Ressource zugreift, wenn diese von keinem anderen Thread
 * benutzt wird.
 */
