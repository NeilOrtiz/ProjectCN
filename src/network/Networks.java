package network;

public class Networks {
    private Parent dad;
    private int secData;
    private Datalink datalink;
    //private Transport transport;


    public Networks (Parent dad) {
        this.dad=dad;
        this.secData=31;
        this.datalink=new Datalink(dad);
        //this.transport=new Transport(dad);
    }

    public void network_receive_from_transport(int channel, int sb, char[] msg,int dest,String srcId,String typeMsg){

        if (typeMsg.equals("d")) {
            byte[] frame=this.n_data_messages(srcId, dest, msg,msg.length);
            int next_hop=this.nextHop(dest);
            datalink.datalink_receive_from_network(channel,sb,frame, next_hop);
        } else {
            String seqNum1=Character.toString(msg[3]);
            String seqNum2=Character.toString(msg[4]);
            int seqNum= Integer.parseInt(seqNum1+seqNum2);
            byte[] frame=this.n_ack_messages(srcId, dest, msg, seqNum);
            //this.printBytes(frame);
            int next_hop=this.nextHop(dest);
            datalink.datalink_receive_from_network(channel,sb,frame, next_hop);
        }
        //System.out.println("[network_receive_from_transport] dest: "+dest);
        // int next_hop=this.nextHop(dest);
        // datalink.datalink_receive_from_network(channel,sb,frame, next_hop);
    }

    private byte[] n_data_messages(String srcId,int dest,char[] msg,int len) {
        int leng=msg.length;
        byte[] frame=new byte[leng+6];

        //int srcid=Integer.parseInt(srcId);
        frame[0]=srcId.getBytes()[0];
        frame[1]= Integer.toString(dest).getBytes()[0];

        //System.out.println("frame[0]: "+frame[0]);
        //System.out.println("msgs[1]: "+frame[1]);

        if (secData<10) {
            frame[2]="0".getBytes()[0];
            frame[3]=Integer.toString(secData).getBytes()[0];
            //frame=this.pushBytes(frame, "0", 2, frame.length);
            //frame=this.pushBytes(frame, Integer.toString(secData), 3, frame.length);
        } else {
            String ssecData=Integer.toString(secData);
            char[] cssecData=ssecData.toCharArray();
            int iPart=Integer.parseInt(Character.toString(cssecData[0]));
            int fPart=Integer.parseInt(Character.toString(cssecData[1]));
            frame[2]=Integer.toString(iPart).getBytes()[0];
            frame[3]=Integer.toString(fPart).getBytes()[0];
        }
        
        if (leng<10) {
            frame[4]="0".getBytes()[0];
            frame[5]=Integer.toString(leng).getBytes()[0];
        } else {
            String sleng=Integer.toString(leng);
            char[] csleng=sleng.toCharArray();
            int iPart=Integer.parseInt(Character.toString(csleng[0]));
            int fPart=Integer.parseInt(Character.toString(csleng[1]));
            frame[4]=Integer.toString(iPart).getBytes()[0];
            frame[5]=Integer.toString(fPart).getBytes()[0];
        }

        int counter=6;
        for (char x:msg) {
            frame[counter]=Character.toString(x).getBytes()[0];
            counter++;
        }
        this.secData++;
        //System.out.println("len: "+leng);
        //System.out.println("frame.length: "+frame.length);
        //this.printBytes(frame);

        return frame;

    }

    private byte[] n_ack_messages(String srcId,int dest,char[] msg,int seqNum){
        int leng=msg.length;
        byte[] frame=new byte[leng+6];

        frame[0]=srcId.getBytes()[0];
        frame[1]= Integer.toString(dest).getBytes()[0];

        if (seqNum<10) {
            frame[2]="0".getBytes()[0];
            frame[3]=Integer.toString(seqNum).getBytes()[0];
            //frame=this.pushBytes(frame, "0", 2, frame.length);
            //frame=this.pushBytes(frame, Integer.toString(secData), 3, frame.length);
        } else {
            String ssecData=Integer.toString(seqNum);
            char[] cssecData=ssecData.toCharArray();
            int iPart=Integer.parseInt(Character.toString(cssecData[0]));
            int fPart=Integer.parseInt(Character.toString(cssecData[1]));
            frame[2]=Integer.toString(iPart).getBytes()[0];
            frame[3]=Integer.toString(fPart).getBytes()[0];
        }

        if (leng<10) {
            frame[4]="0".getBytes()[0];
            frame[5]=Integer.toString(leng).getBytes()[0];
        } else {
            String sleng=Integer.toString(leng);
            char[] csleng=sleng.toCharArray();
            int iPart=Integer.parseInt(Character.toString(csleng[0]));
            int fPart=Integer.parseInt(Character.toString(csleng[1]));
            frame[4]=Integer.toString(iPart).getBytes()[0];
            frame[5]=Integer.toString(fPart).getBytes()[0];
        }

        int counter=6;
        for (char x:msg) {
            frame[counter]=Character.toString(x).getBytes()[0];
            counter++;
        }

        return frame;
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

    private int nextHop(int dest){
        int temp=-1;

        temp=dad.routingTable.get(dest);

        return temp;
    }

    public void network_receive_from_datalink(char[] msg, int neighbor_id, char channel,String type) {
        Transport transport=new Transport(dad);

        if (type.equals("d")) {

            String dest= Character.toString(msg[2]);

            if (dad.myID.equals(dest)) {
                transport.transport_receive_from_network(msg,channel);
            } else {
                // Routing
            }

        } else {
            transport.transport_receive_from_network(msg,channel);
        }
        
        

    }

    // private byte[] pushBytes(byte[] frame, String input,int offset,int len){
    //     byte[] temp=new byte[len+input.length()];
    //     byte[] bytesFrame=input.getBytes();
        
    //     int counter=0;
    //     for (int i=offset;i<(bytesFrame.length+offset);i++) {
    //     	frame[i]=bytesFrame[counter];
    //         counter++;
    //     }
    //     temp=frame;
    //     return temp;
    // }
}