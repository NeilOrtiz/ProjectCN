package network;

public class Shutdown extends Thread {
    private Parent dad;

    public Shutdown (Parent dad) {
        this.dad=dad;
    }

    @Override
    public void run() {

        try {
            //Thread.sleep(5*dad.end*1000);
            Thread.sleep(dad.end*1000);
            // Thread.sleep(10000*1000);
        } catch (InterruptedException ex) {
            System.out.println(ex.getStackTrace());
        }
        
        dad.terminate();
    }
}