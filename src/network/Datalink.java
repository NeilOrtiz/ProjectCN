package network;

//import java.io.File;

public class Datalink {
    private Parent dad;
    //private Networks networks;

    public Datalink (Parent dad) {
        this.dad=dad;
        //this.networks=new Networks(dad);
    }

    public void datalink_receive_from_network(int channel, int sb,byte[] msg, int next_hop) {
        char dest=(char) msg[1];
        String type;
        String fileName=".//"+"from"+dad.myID+"to"+dest+".txt";
        Writer write = new Writer(fileName);

        char tp=(char) msg[6];
        if (tp=='d') {
            type="data";
        } else {
            type="ack";
        }

        char secNum1= ((char) msg[2]);
        char secNum2= ((char) msg[3]);
        String secNum=Character.toString(secNum1)+Character.toString(secNum2);
        char[] ntwMsg=this.getntwMsg(msg);
        String msgStuffing=this.stuffing(ntwMsg);
        write.writeFile(type,channel,secNum,msgStuffing);
    }

    public void datalink_receive_from_channel(){
        Networks networks=new Networks(dad);
        String[] ngbs;
        ngbs=dad.ngbs.split(",");
        Reader reader=new Reader();

        for (String x:ngbs) {
            String pathname=".//"+"from"+x+"to"+dad.myID+".txt";            
            String str=reader.readFile(pathname, dad);

            if (str.equals("null")){
                //System.err.println("[Datalink] Esta vacio");
            } else {
                //System.err.println("[Datalink] NO esta vacio");
                char[] msg1=this.convertToChar(str);
                //char[] msg2=this.getdtlMsg(msg1);
                //networks.network_receive_from_datalink(msg, Integer.parseInt(x));
                for (char y:msg1) {
                    System.out.print(y);
                }
                System.out.println("");
                char[] msg=this.unstuffing(msg1);
                for (char y:msg) {
                    System.out.print(y);
                }
                System.out.println("");
            }
        }
        
    }

    public char[] getntwMsg(byte[] msg){
        char[] temp=new char[msg.length-6];
        int counter=0;
        for (int i=6;i<=msg.length-1;i++) {
            temp[counter]=(char) msg[i];
            counter++;
        }
        return temp;
    }

    public String stuffing(char[] ntwMsg) {
        String temp="F";

        for (char x:ntwMsg) {
            if (x=='F'||x=='E'||x=='f'||x=='e'||x=='X'||x=='x') {
                temp=temp+"X"+x;
            } else {
                temp=temp+x;
            }
        }
        temp=temp+"E";

        return temp;
    }

    public char[] convertToChar(String str){
        //char[] temp=new char[str.length()];
        char[] temp=str.toCharArray();
        System.out.println("[convertToChar] str.length:" +str.length());
        // for (int i=0;i<=(str.length()-1);i++) {
            
        //     temp[i]=
        // }

        return temp;
    }

    public char[] getdtlMsg(char[] msg){
        char[] temp=new char[msg.length-10];
        int counter=0;
        for (int i=10;i<=msg.length-1;i++) {
            temp[counter]=(char) msg[i];
            counter++;
        }
        return temp;
    }

    public char[] unstuffing(char[] msg) {
        char[] ans=new char[msg.length-10+3];
        boolean startMsg=false;
        char temp='*';
        //System.out.println("[unstuffing] ans.length: "+ans.length);
        int counter=0;
        for (int i=0;i<=msg.length-1;i++) {
            // if ((msg[i]=='X') && (msg[i]!='X')){
            //     temp[counter]=msg[i+1];
            // } else {

            // }
            //if ((msg[i]=='X')&&((msg[i+1]=='X')||(msg[i+1]=='F')||(msg[i+1]=='E'))) {
                // do nothing

            // if ((msg[i]=='F')&&(msg[i-1]!='X')) {
            //     startMsg=true;
            //     counter++;
            // } else if ((startMsg==true)&&(msg[i]!='X')) {
            //     temp[counter]=msg[i+1];
            //     counter++;    
            // } else if ((startMsg==true)&&(msg[i]=='X')&&((msg[i+1]=='X')||(msg[i+1]=='F')||(msg[i+1]=='E'))) {
            //     // do nothing
            // } else if ((startMsg==true)&&(msg[i]=='E')&&(msg[i-1]=='X')) {
            //     temp[counter]=msg[i];
            //     counter++;
            // } else if ((startMsg==true)&&(msg[i]=='E')&&(msg[i-1]!='X')) {
            //     // do nothing: end the msg reached
            // } else if ((startMsg==true)&&(msg[i]=='F')&&(msg[i-1]=='X')) {
            //     temp[counter]=msg[i];
            //     counter++;
            // } else if ((startMsg==true)&&(msg[i]=='F')&&(msg[i-1]=='X')) {
            //     temp[counter]=msg[i];
            //     counter++;
            // }

            if (Character.toString(msg[i]).matches("F")&&(!startMsg)) {
                //System.out.println("[unstuffing] i: "+i);
                //System.out.println("[unstuffing] msg[i]: "+msg[i]);
                startMsg=true;
                temp=msg[i];
            } else if ((startMsg)&&(!Character.toString(msg[i]).matches("F"))&&(!Character.toString(msg[i]).matches("E"))&&(!Character.toString(msg[i]).matches("X"))) {
                ans[counter]=msg[i];
                counter++;
                temp=msg[i];
            } else if ((startMsg)&&(Character.toString(msg[i]).matches("X"))&&(!Character.toString(temp).matches("X"))) {
                temp=msg[i];
            } else if ((startMsg)&&((Character.toString(msg[i]).matches("F")))&&(Character.toString(temp).matches("X"))) {
                ans[counter]=msg[i];
                counter++;
                temp=msg[i];
            
            } else if ((startMsg)&&((Character.toString(msg[i]).matches("E")))&&(Character.toString(temp).matches("X"))) {
                ans[counter]=msg[i];
                counter++;
                temp=msg[i];
            } else if ((startMsg)&&(Character.toString(msg[i]).matches("X"))&&(Character.toString(temp).matches("X"))) {
                ans[counter]=msg[i];
                counter++;
                //temp=msg[i];
                temp='*';

            } else if ((startMsg)&&(Character.toString(msg[i]).matches("E"))&&(!Character.toString(temp).matches("X"))) {
                startMsg=false;
            }
            
            //if ((msg[i]=='F')) {
            // if (Character.toString(msg[i]).matches("F")) {
            //     startMsg=true;
            //     temp=msg[i];
            // } else if ((startMsg=true)&&(msg[i]!='F')&&(msg[i]!='E')&&(msg[i]!='X')) {
            //     System.out.println("[unstuffing] counter: "+counter);
            //     System.out.println("[unstuffing] msg[i]: "+msg[i]);
            //     ans[counter]=msg[i];
            //     counter++;
            //     temp=msg[i];
            // } else if ((startMsg=true)&&(msg[i]=='X')&&(temp!='X')) {
            //     temp=msg[i];
            // } else if ((startMsg=true)&&((msg[i]=='F')||(msg[i]=='E')||(msg[i]=='X'))&&(temp=='X')) {
            //     ans[counter]=msg[i];
            //     counter++;
            //     temp=msg[i];
            // } else if ((startMsg=true)&&(msg[i]=='E')&&(temp!='X')) {
            //     startMsg=false;
            // }
        }

        return ans;
    }
}