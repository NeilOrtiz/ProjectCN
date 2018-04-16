package network;

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

        if (sizeMsg<=WINDOW_SIZE) {

            char[] frame = this.data_messages(srcId, dstID, msg.toCharArray());
            secData++;
            networks.network_receive_from_transport(frame, frame.length, Integer.parseInt(dstID));  
        } else {
            //Split msg
            int offset=0;
            int len=25;
            for (int i=1;i<=numMsg;i++) {
                char[] pmsg=new char[WINDOW_SIZE];
                char[] nmsg=null;
                for (int j=0;j<=24;j++) {
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
                secData++;
                networks.network_receive_from_transport(frame, frame.length, Integer.parseInt(dstID));
            }
        }
    }

    private void transport_layer_msg(int srcId,int dstID, String msg){
        //TODO: transport_layer_msg()
    }

    private char[] data_messages(String srcId,String dstID, char[] msg){

        int len=msg.length;
        char[] msgs=new char[len+5];
        //char [] frame=new char[len];

        //byte[] frame=new byte[30];
        
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
        // for (char x:msg_arr) {
        //     msgs[counter]=x;
        //     counter++;
        // }

        // frame[0]=100;
        // frame=this.pushBytes(frame, srcId, 1);//byte source id (from "0" up to "9")
        // frame=this.pushBytes(frame, dstID, 2);//byte destination id (from "0" up to "9") 
        
        // if (secData<10) { //byte sequence number (from "00" to "99")
        //     frame=this.pushBytes(frame, "0", 3);
        //     frame=this.pushBytes(frame,Integer.toString(secData), 4); 
        // } else {
        //     frame=this.pushBytes(frame,Integer.toString(secData), 3);
        // }

        // frame=this.pushBytes(frame,msg,5);

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