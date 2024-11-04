public class Main{

    public static void main(String args[]) {

        int i = 1;
        while(i < 10) {
            Thread t1 = new Thread(new Counter(i));
            i++;
            t1.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

/**
 * This simple program creates a total number of 5 Threads. They're executed in a for loop. If you take a look into
 * the implementation of the Counter-class you can see they each just count up 5 and print out their "ID" and
 * iteration number.
 * By implementing the "run" method of the "Thread" into the subclass counter, we are able to use multithreading.
 * By starting the 5 Threads, which need to share CPU-time, since there is no real parallel working of threads
 * (in terms of several Threads working at the exact time), you can see, that their Output is interleaving. Example:
 * I'm Thread 5 and in my iteration number 1
 * I'm Thread 5 and in my iteration number 2
 * I'm Thread 5 and in my iteration number 3
 * I'm Thread 5 and in my iteration number 4
 * I'm Thread 5 and in my iteration number 5
 * I'm Thread 1 and in my iteration number 1
 * I'm Thread 2 and in my iteration number 1
 * I'm Thread 2 and in my iteration number 2
 * I'm Thread 2 and in my iteration number 3
 * I'm Thread 2 and in my iteration number 4
 * I'm Thread 2 and in my iteration number 5
 * I'm Thread 4 and in my iteration number 1
 * I'm Thread 3 and in my iteration number 1
 * I'm Thread 1 and in my iteration number 2
 * I'm Thread 1 and in my iteration number 3
 * I'm Thread 1 and in my iteration number 4
 * I'm Thread 1 and in my iteration number 5
 * I'm Thread 4 and in my iteration number 2
 * I'm Thread 4 and in my iteration number 3
 * I'm Thread 4 and in my iteration number 4
 * I'm Thread 3 and in my iteration number 2
 * I'm Thread 3 and in my iteration number 3
 * I'm Thread 3 and in my iteration number 4
 * I'm Thread 3 and in my iteration number 5
 * I'm Thread 4 and in my iteration number 5
 *
 * You can see that the threats are executed in a, what seems random, order. This order is due to the scheduling, which
 * splits CPU-Ressources between the Threads.
 * Furthermore you can notice, that a threat doesn't need to come to a finish of its execution, before another Thread
 * starts its execution.
 *
 * Those effects of the last to paragraphs can be prevented by using the join()-method right after the start()-method.
 */



