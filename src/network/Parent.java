package network;

public class Parent {
    public int myID;
    public int duration;
    public int dstID;
    public String msg;
    public int ng1;
    public int ng2;

    public Parent (int myID, int duration,int dstID, String msg, int ng1, int ng2) {
        this.myID=myID;
        this.duration=duration;
        this.dstID=dstID;
        this.msg=msg;
        this.ng1=ng1;
        this.ng2=ng2;
    }


    public static void main (String[] args) {
        int myID,duration,dstID,ng1,ng2;
        String msg;
        
        if (args.length!=5) {
			System.err.println("Usage: java -jar ProjectCN/dist/Parent.jar <id> <duration> <id dst> <msg> <id's neighboors>");
			System.exit(1);
        }

        myID=Integer.parseInt(args[0]);
        duration= Integer.parseInt(args[1]);
        dstID=Integer.parseInt(args[3]);
        msg=args[4];
        ng1=Integer.parseInt(args[5]);
        ng2=Integer.parseInt(args[6]);

        Parent dad = new Parent(myID, duration, dstID, msg, ng1, ng2);

    }

}