class Counter extends Thread{

    private int number;

    public Counter(int pNum){
        this.number = pNum;
    }

    @Override
    public void run(){
        for(int i = 1; i<=5; i++){
            System.out.println("I'm Thread " + number + " and in my iteration number " + i);
        }
    }
}


