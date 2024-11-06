class Counter implements Runnable{

    private int number;

    public Counter(int pNum){
        this.number = pNum;
    }

    @Override
    public void run(){
        for(int i=1; i<=5; i++) {
            System.out.println("I'm Thread " + number + "in Iteration number " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


