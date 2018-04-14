package network;

public class Parent {
    public String myID;
    public int duration;
    public String dstID;
    public String msg;
    public int ng1;
    public int ng2;

    public Parent (String myID, int duration,String dstID, String msg, int ng1, int ng2) {
        this.myID=myID;
        this.duration=duration;
        this.dstID=dstID;
        this.msg=msg;
        this.ng1=ng1;
        this.ng2=ng2;
    }


    public static void main (String[] args) {
        int duration,ng1,ng2;
        String msg,myID,dstID;
        
        if (args.length!=6) {
			System.err.println("Usage: java -jar ProjectCN/dist/Parent.jar <id> <duration> <id dst> <msg> <id's neighboors>");
			System.exit(1);
        }

        myID=args[0];
        duration= Integer.parseInt(args[1]);
        dstID=args[2];
        msg=args[3];
        ng1=Integer.parseInt(args[4]);
        ng2=Integer.parseInt(args[5]);

        Parent dad = new Parent(myID, duration, dstID, msg, ng1, ng2);

        Transport transport = new Transport(dad);

        transport.transport_send_string(dad.myID,dad.dstID,dad.msg);

    }

}