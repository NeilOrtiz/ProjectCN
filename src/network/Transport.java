package network;

import java.util.ArrayList;

public class Transport {
    private Parent dad;
    //public int secData;
    private Networks networks;
    public static final int WINDOW_SIZE=25;

    public Transport (Parent dad) {
        this.dad=dad;
        //this.secData=23;
        this.networks=new Networks(dad);
    }

    public void transport_send_string(String srcId,String dstID, String msg){
        
        int sizeMsg=msg.length();
        int numMsg=(int)Math.ceil((double)sizeMsg/25d);
        char[] msg_arr=msg.toCharArray();
        ArrayList<char[]> channel0 = new ArrayList<char[]>(4);
        ArrayList<char[]> channel1 = new ArrayList<char[]>(4);

        if (sizeMsg<=WINDOW_SIZE) {
            //secData++;
            char[] frame = this.data_messages(srcId, dstID, msg.toCharArray());
            channel0.add(frame);
            
            //System.out.println("secData: "+secData);
            // if (dad.sb[0]==dad.ab[0]) {
            //     //dad.sb[0]=(dad.sb[0]+1)%2;
            //     dad.sb[0]=dad.secData;
            //     networks.network_receive_from_transport(0,dad.sb[0],frame, Integer.parseInt(dstID),dad.myID,"d");
            // }

            dad.sb[0]=dad.secData;
            networks.network_receive_from_transport(0,dad.sb[0],frame, Integer.parseInt(dstID),dad.myID,"d");

            // while (!llego) {
            //     try {
            //         Thread.sleep(5000);
            //     } catch (InterruptedException ex) {
            //         System.out.println(ex.getStackTrace());
            //     }

            //     System.out.println("[transport_send_string] dad.sb[0]: "+dad.sb[0]+", dad.ab[0]: "+dad.ab[0]);
            //     if (dad.sb[0]==dad.ab[0]) {
            //         llego=true;
            //     } else {
            //         networks.network_receive_from_transport(0,dad.sb[0],frame, Integer.parseInt(dstID),dad.myID,"d");
            //     }
            // }

            // System.out.println("5 seconds to finish");

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
                //secData++;
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

    public void transport_receive_from_network(char[] msg,char channel) {
        // System.out.println("[transport_receive_from_network]");
        // for (char y:msg) {
        //     System.out.print(y);
        // }
        // System.out.println("");

        String type=Character.toString(msg[0]);

        if (type.equals("d")) {
            String frame=this.getMsg(msg);
            String src=Character.toString(msg[1]);
            this.transport_output_all_received(src, frame);

            String dstID=Character.toString(msg[2]);
            String seqNum1=Character.toString(msg[3]);
            String seqNum2=Character.toString(msg[4]);
            char[] ack=this.ack_message(src, dstID, seqNum1, seqNum2);
            //System.out.println("Channel: "+channel);
            //System.out.println("secData: "+Integer.parseInt(seqNum1+seqNum2));
            //System.out.println("dad.ab[1]: "+dad.ab[1]);
            //int secuenica=Integer.parseInt(seqNum1+seqNum2);
            int chan=Integer.parseInt(Character.toString(channel)) ;
            
            //dad.modAc(chan, secuenica);
            //dad.ab[channel]=secuenica;
            //System.out.println("Channel2: "+channel);
            networks.network_receive_from_transport(chan, 0, ack, Integer.parseInt(src), dad.myID,"a");

            // for (char y:ack) {
            //     System.out.print(y);
            // }
            // System.out.println("");
            
        } else {
            //System.out.println("Este es un ACk");

            String seqNum1=Character.toString(msg[6]);
            String seqNum2=Character.toString(msg[7]);
            int secuenica=Integer.parseInt(seqNum1+seqNum2);

            int canal= Integer.parseInt(Character.toString(msg[4]));
            dad.modAc(canal, secuenica);

            //System.out.println("ab number: "+dad.ab[canal]);
            //System.out.println("sb number: "+dad.sb[canal]);


            // for (char y:msg) {
            //     System.out.print(y);
            // }
            // System.out.println("");

        }

    }

    private char[] data_messages(String srcId,String dstID, char[] msg){

        int len=msg.length;
        char[] msgs=new char[len+5];
        
        msgs[0]='d';
        msgs[1]=srcId.charAt(0);
        msgs[2]=dstID.charAt(0);
        if (dad.secData<10) {
            msgs[3]='0';
            msgs[4]=Integer.toString(dad.secData).charAt(0) ;
        } else {
            String ssecData=Integer.toString(dad.secData);
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

    private char[] ack_message(String srcId,String dstID,String seqNum1,String seqNum2) {

        char[] msgs=new char[5];

        msgs[0]='a';
        msgs[1]=srcId.charAt(0);
        msgs[2]=dstID.charAt(0);
        msgs[3]=seqNum1.charAt(0);
        msgs[4]=seqNum2.charAt(0);
        return msgs;

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

    private void transport_output_all_received(String src,String msg){
        //TODO:
        Writer writer = new Writer();
        String pathname=".//"+"node"+dad.myID+"received"+".txt";
        String str= "From "+src+" received: "+msg;
        writer.writeFile(str, pathname);
    }

    private String getMsg(char[] msg){
        
        String frame="";
        
        for (int i=5;i<=(msg.length-1);i++) {
            frame=frame+Character.toString(msg[i]);
        }
        return frame;
    }
}