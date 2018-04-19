package network;

import java.util.Hashtable;
//import java.util.Set;
//import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

public class Parent {
    public String myID;
    public int duration;
    public String dstID;
    public String msg;
    public String ngbs;
    public Hashtable<Integer,Integer> routingTable;
    public int[] sb;
    public int[] ab;
    public int end;

    public Parent (String myID, int duration,String dstID, String msg, String ngbs,int end) {
        this.myID=myID;
        this.duration=duration;
        this.dstID=dstID;
        this.msg=msg;
        this.ngbs=ngbs;
        this.routingTable=new Hashtable<Integer,Integer>();
        this.sb=new int[] {0,0};
        this.ab=new int[] {0,0};
        this.end=end;
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
        
        
        Parent dad = new Parent(myID, duration, dstID, msg, ngb,duration);
        Shutdown shutdown=new Shutdown(dad);
        shutdown.start();
        //dad.routingTable.put();
        
        //Populate the Routing Table
        
        routingTablePopulate(dad);
        channelsCreation(dad);
        
        // Set<Integer> keys= dad.routingTable.keySet();
        // for (int key:keys) {
        //     System.out.println("key: "+key+", value: "+dad.routingTable.get(key));
        // }
        skeleton(dad);

        
    }

    public static void channelsCreation( Parent dad) {
        String[] ngbs;
        ngbs=dad.ngbs.split(",");
        for (String x:ngbs) {
            String fileName=".//"+"from"+dad.myID+"to"+x+".txt";
            File file = new File(fileName);
            
            try {
                if (file.createNewFile()){
                    //System.out.println("File is created!");
                }else{
                    //System.out.println("File already exists.");
                }
            } catch (IOException ex) {
                System.err.println("[ERROR] File couldn't be created");
                ex.printStackTrace();
            }

            //System.out.println("[Parent] fileName: "+fileName);

        }

    }

    public static void routingTablePopulate(Parent dad) {
        String[] ngbs;
        ngbs=dad.ngbs.split(",");
        for (String x:ngbs) {
            dad.routingTable.put(Integer.parseInt(x),Integer.parseInt(x));
        }
    }

    public void terminate() {
        System.exit(1);
    }

    public static void skeleton(Parent dad) {

        Transport transport = new Transport(dad);
        Datalink datalink=new Datalink(dad);
        transport.transport_send_string(dad.myID,dad.dstID,dad.msg);
        
        for (int i=0;i<=dad.end;i++){
            datalink.datalink_receive_from_channel();
            

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println(ex.getStackTrace());
            }
        }
    }

}