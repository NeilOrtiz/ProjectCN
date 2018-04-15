package network;

public class Transport {
    private Parent dad;
    private int secData;


    public Transport (Parent dad) {
        this.dad=dad;
        this.secData=0;
    }

    public void transport_send_string(String srcId,String dstID, String msg){
        byte[] frame = this.data_messages(srcId, dstID, msg);
        //System.out.println("msg size: "+msg.getBytes().length);
        //this.printBytes(frame);

    }

    private void transport_layer_msg(int srcId,int dstID, String msg){
        //TODO: transport_layer_msg()
    }

    private byte[] data_messages(String srcId,String dstID, String msg){

        byte[] frame=new byte[30];
        
        frame[0]=100;
        frame=this.pushBytes(frame, srcId, 1);//byte source id (from "0" up to "9")
        frame=this.pushBytes(frame, dstID, 2);//byte destination id (from "0" up to "9") 
        
        if (secData<10) { //byte sequence number (from "00" to "99")
            frame=this.pushBytes(frame, "0", 3);
            frame=this.pushBytes(frame,Integer.toString(secData), 4); 
        } else {
            frame=this.pushBytes(frame,Integer.toString(secData), 3);
        }

        frame=this.pushBytes(frame,msg,5);

        return frame;
    }

    private void ack_messages() {
        //TODO: ack_messages()
    }

    private byte[] pushBytes(byte[] frame, String input,int offset){
        byte[] temp=new byte[30];
        byte[] bytesFrame=input.getBytes();
        
        int counter=0;
        for (int i=offset;i<(bytesFrame.length+offset);i++) {
        	frame[i]=bytesFrame[counter];
            counter++;
        }
        temp=frame;
        return temp;
    }

    private void printBytes(byte[] frame) {

        for (byte x:frame) {
            System.out.print(x);
            System.out.print(" ");
        }

        System.out.println("");

        for (byte x:frame) {
        	System.out.print((char)x);
        }
        System.out.println("");

    }
}