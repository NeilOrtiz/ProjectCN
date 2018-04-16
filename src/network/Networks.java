package network;

public class Networks {
    private Parent dad;
    private int secData;


    public Networks (Parent dad) {
        this.dad=dad;
        this.secData=12;
    }

    public void network_receive_from_transport(char[] msg,int len, int dest,String srcId){
        // for (char x:msg) {
        //     System.out.print(x);
        // }
        // System.out.println("    Length msg: "+len);
        //int srcId=(int)msg[1];
        //int dstID=(int)msg[2];

        //System.out.println("srcId: "+srcId);

        byte[] frame=this.n_data_messages(srcId, dest, msg,len);

    }

    private byte[] n_data_messages(String srcId,int dest,char[] msg,int len) {
        int leng=msg.length;
        byte[] frame=new byte[leng+4];

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
        frame[4]=Integer.toString(len).getBytes()[0];
        System.out.println("len: "+leng);
        System.out.println("frame.length: "+frame.length);
        this.printBytes(frame);

        return frame;

    }

    private byte[] pushBytes(byte[] frame, String input,int offset,int len){
        byte[] temp=new byte[len+input.length()];
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