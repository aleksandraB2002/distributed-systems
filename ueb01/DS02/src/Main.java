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
 * In diesem Beispiel werden 20 Threads erstellt, welche jeweils 1000x die Methoden zum inkrementieren und dekrementieren
 * des Zählers desselben Objekts aufrufen. Dadurch kommt es dazu, dass die Threads sich durch das Scheduling zu vorher
 * undefinierten Zeitpunkten während ihrer Ausführung mit ihrer Ausführung "abwechseln" (übergabe von Ressourcen
 * des Rechners an anderen Thread). Dadurch kann es zu inkonstanten Werten der globalen Variable c des Counter-Objekts kommen.
 * Ein neuer Thread (t2) kann beispielsweise einen veralteten Wert von c auslesen, da ein Thread (t1) vor dem Überschreiben des
 * Wertes von c in seiner Ausführung unterbrochen wurde und t2 somit noch den alten Wert überschreibt.
 */