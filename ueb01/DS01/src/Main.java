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


