package network;

import java.util.Hashtable;
import java.util.Set;
import java.util.ArrayList;

public class Parent {
    public String myID;
    public int duration;
    public String dstID;
    public String msg;
    public String ngbs;
    public Hashtable<Integer,Integer> routingTable;

    public Parent (String myID, int duration,String dstID, String msg, String ngbs) {
        this.myID=myID;
        this.duration=duration;
        this.dstID=dstID;
        this.msg=msg;
        this.ngbs=ngbs;
        this.routingTable=new Hashtable<Integer,Integer>();
    }


    public static void main (String[] args) {
        int duration;
        String msg,myID,dstID,ngb;
       
        
        if (args.length!=5) {
			System.err.println("Usage: java -jar ProjectCN/dist/Parent.jar <id> <duration> <id dst> <msg> <id's neighboors: \"id1,id2\">");
			System.exit(1);
        }

        myID=args[0];
        duration= Integer.parseInt(args[1]);
        dstID=args[2];
        msg=args[3];
        ngb=args[4];
        // if (!args[5].isEmpty()){
        //     ng2=Integer.parseInt(args[5]);
        // } else {
        //     ng2=-1;
        // }
        

        Parent dad = new Parent(myID, duration, dstID, msg, ngb);
        //dad.routingTable.put();
        
        String[] ngbs;
        ngbs=ngb.split(",");
        for (String x:ngbs) {
            dad.routingTable.put(Integer.parseInt(x),Integer.parseInt(x));
        }
        

        // Set<Integer> keys= dad.routingTable.keySet();
        // for (int key:keys) {
        //     System.out.println("key: "+key+", value: "+dad.routingTable.get(key));
        // }


        Transport transport = new Transport(dad);

        transport.transport_send_string(dad.myID,dad.dstID,dad.msg);

    }

}