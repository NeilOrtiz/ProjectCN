package network;

import java.util.ArrayList;

public class Transport {
    private Parent dad;
    private int secData;
    private Networks networks;
    public static final int WINDOW_SIZE=25;


    public Transport (Parent dad) {
        this.dad=dad;
        this.secData=0;
        this.networks=new Networks(dad);
    }

    public void transport_send_string(String srcId,String dstID, String msg){
        
        int sizeMsg=msg.length();
        int numMsg=(int)Math.ceil((double)sizeMsg/25d);
        char[] msg_arr=msg.toCharArray();
        //int cols=(int)Math.ceil((double)numMsg/2);
        ArrayList<char[]> channel0 = new ArrayList<char[]>(4);
        ArrayList<char[]> channel1 = new ArrayList<char[]>(4);

        if (sizeMsg<=WINDOW_SIZE) {
            char[] frame = this.data_messages(srcId, dstID, msg.toCharArray());
            channel0.add(frame);
            secData++;
            if (dad.sb[0]==dad.ab[0]) {
                dad.sb[0]=(dad.sb[0]+1)%2;
                networks.network_receive_from_transport(0,dad.sb[0],frame, Integer.parseInt(dstID),dad.myID);
            }
            
              
        } else {
            //Split msg
            int offset=0;
            int len=25;
            for (int i=1;i<=numMsg;i++) {
                char[] pmsg=new char[WINDOW_SIZE];
                char[] nmsg=null;
                for (int j=0;j<=WINDOW_SIZE;j++) {
                    len=j;
                    nmsg=new char[len];
                    try{
                        pmsg[j]=msg_arr[offset];
                        for (int k=0;k<=(len-1);k++) {
                            nmsg[k]=pmsg[k];
                        }
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        for (int k=0;k<=(len-1);k++) {
                            nmsg[k]=pmsg[k];
                        }
                        break;
                    }
                    offset++;
                }
                char[] frame = this.data_messages(srcId, dstID, nmsg);

                if ((i%2)!=0) {
                    channel0.add(frame);
                } else {
                    channel1.add(frame);
                }
                secData++;
                //networks.network_receive_from_transport(frame, nmsg.length, Integer.parseInt(dstID),dad.myID);
            }
            // System.out.println("[transport_send_string] longitud channel0: "+channel0.size());
            // System.out.println("CHANNEL 0");
            // for (char[] x:channel0) {
            //     //System.out.println("[transport_send_string] channel0: "+x);
            //     for (char y:x) {
            //         System.out.print(y);
            //     }
            //     System.out.println("");
            // }
            // System.out.println("");
            // System.out.println("CHANNEL 1");
            // for (char[] x:channel1) {
            //     //System.out.println("[transport_send_string] channel0: "+x);
            //     for (char y:x) {
            //         System.out.print(y);
            //     }
            //     System.out.println("");
            // }
        }
        // System.out.println("[transport_send_string] longitud channel0: "+channel0.size());
        // System.out.println("CHANNEL 0");
        // for (char[] x:channel0) {
        //     //System.out.println("[transport_send_string] channel0: "+x);
        //     for (char y:x) {
        //         System.out.print(y);
        //     }
        //     System.out.println("");
        // }
        // System.out.println("");
    }

    private void transport_layer_msg(int srcId,int dstID, String msg){
        //TODO: transport_layer_msg()
    }

    private char[] data_messages(String srcId,String dstID, char[] msg){

        int len=msg.length;
        char[] msgs=new char[len+5];
        
        msgs[0]='d';
        msgs[1]=srcId.charAt(0);
        msgs[2]=dstID.charAt(0);
        if (secData<10) {
            msgs[3]='0';
            msgs[4]=Integer.toString(secData).charAt(0) ;
        } else {
            String ssecData=Integer.toString(secData);
            char[] cssecData=ssecData.toCharArray();
            int iPart=Integer.parseInt(Character.toString(cssecData[0]));
            int fPart=Integer.parseInt(Character.toString(cssecData[1]));
            msgs[3]=Long.toString(iPart).charAt(0) ;
            msgs[4]=Integer.toString(fPart).charAt(0);
        }

        char[] msg_arr=msg;

        int counter=5;
        for (char x:msg_arr) {
            msgs[counter]=x;
            counter++;
        }

        return msgs;
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