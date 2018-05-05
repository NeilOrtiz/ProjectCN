package network;

public class Skeleton extends Thread {
    private Parent dad;
    private String dstID;
    private String msg;

    public Skeleton (Parent dad, String dstID, String msg) {
        this.dad=dad;
        this.dstID=dstID;
        this.msg=msg;
    }

    @Override
    public void run() {

        Transport transport = new Transport(dad);
        Datalink datalink=new Datalink(dad);
        boolean llego=false;
        int counter=0;

        for (int i=0;i<=dad.end;i++){

            if (dad.isMsg){
                if (!llego) {
                    if ((counter%5)==0) {
                        transport.transport_send_string(dad.myID,dstID,msg);
                    }
                }                
            }

            datalink.datalink_receive_from_channel();

            if (dad.sb[0]==dad.ab[0]) {
                llego=true;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println(ex.getStackTrace());
            }
            counter++;
        }

    }
}