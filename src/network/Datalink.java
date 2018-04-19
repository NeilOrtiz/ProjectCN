package network;

public class Datalink {
    private Parent dad;

    public Datalink (Parent dad) {
        this.dad=dad;
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

        String[] ngbs;
        ngbs=dad.ngbs.split(",");

        for (String x:ngbs) {
            String pathname="from"+x+"to"+dad.myID+".txt";
            Reader reader=new Reader(pathname, dad);
            reader.readFile();
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
}