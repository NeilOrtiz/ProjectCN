package network;

//import java.io.File;

public class Datalink {
    private Parent dad;

    public Datalink (Parent dad) {
        this.dad=dad;
    }

    public void datalink_receive_from_network(int channel, int sb,byte[] msg, int next_hop) {
        char dest=(char) msg[1];
        String type;
        String fileName=".//"+"from"+dad.myID+"to"+dest+".txt";
        Writer write = new Writer();

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
        String str=type+" "+channel+" "+secNum+" "+msgStuffing;
        write.writeFile(str,fileName);
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
                
                // for (char y:msg1) {
                //     System.out.print(y);
                // }
                // System.out.println("");
                char[] msg=this.unstuffing(msg1);
                // for (char y:msg) {
                //     System.out.print(y);
                // }
                // System.out.println("");

                networks.network_receive_from_datalink(msg, Integer.parseInt(x));
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

    public char[] convertToChar(String str) {
        char[] temp=str.toCharArray();
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
        int counter=0;
        for (int i=0;i<=msg.length-1;i++) {
            if (Character.toString(msg[i]).matches("F")&&(!startMsg)) {
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
        }
        return ans;
    }
}