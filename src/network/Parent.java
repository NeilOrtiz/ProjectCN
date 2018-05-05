package network;

import java.util.Hashtable;
import java.util.Set;
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
    public Hashtable<Integer,Integer> ackTable;
    public Hashtable<String,Integer> readOffset;
    public int[] sb;
    public int[] ab;
    public int end;
    public boolean isMsg;
    public int secData;

    public Parent (String myID, int duration,String dstID, String msg, String ngbs,int end,boolean isMsg) {
        this.myID=myID;
        this.duration=duration;
        this.dstID=dstID;
        this.msg=msg;
        this.ngbs=ngbs;
        this.routingTable=new Hashtable<Integer,Integer>();
        this.ackTable=new Hashtable<Integer,Integer>();
        this.readOffset=new Hashtable<String,Integer>();
        this.sb=new int[] {0,0};
        this.ab=new int[] {0,0};
        this.end=end;
        this.isMsg=isMsg;
        this.secData=23;
    }

    public static void main (String[] args) {
        int duration=-1;
        String msg=null,myID=null,dstID=null,ngb=null;
        boolean m=true;
       
        
        if (args.length!=5) {
            
            if (args[0].equals(args[2])) {
                myID=args[0];
                duration= Integer.parseInt(args[1]);
                dstID=args[2];
                ngb=args[3];
                m=false;
                msg=null;
                
            } else {
                System.err.println("Usage: java -jar ProjectCN/dist/Parent.jar <id> <duration> <id dst> <msg> <id's neighboors: \"id1,id2\">");
                System.exit(1);
            }
			
        }

        if (m) {
        myID=args[0];
        duration= Integer.parseInt(args[1]);
        dstID=args[2];
        msg=args[3];
        ngb=args[4];
        }
        
        // if (!args[5].isEmpty()){
        //     ng2=Integer.parseInt(args[5]);
        // } else {
        //     ng2=-1;
        // }
        
        
        Parent dad = new Parent(myID, duration, dstID, msg, ngb,duration,m);
        Shutdown shutdown=new Shutdown(dad);
        shutdown.start();
        //dad.routingTable.put();
        
        //Populate the Routing Table
        
        routingTablePopulate(dad);
        channelsCreation(dad);
        ackTableInitialization(dad);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println(ex.getStackTrace());
        }

        boolean isflood=isMyNeighbor(dad);

        if ((isflood)) {
            Skeleton skeleton = new Skeleton(dad, dstID, msg);
            skeleton.start();
        } else {
            if (!dad.isMsg) {
                Skeleton skeleton = new Skeleton(dad, dstID, msg);
                skeleton.start();
            } else {
                System.out.println("Flood is necesary");
            }
            
        }

        
        //skeleton(dad);
        
    }

    public static void channelsCreation( Parent dad) {
        String[] ngbs;
        ngbs=dad.ngbs.split(",");
        for (String x:ngbs) {
            String fileName=".//"+"from"+dad.myID+"to"+x+".txt";
            String fileNameRead=".//"+"from"+x+"to"+dad.myID+".txt";
            dad.readOffset.put(fileNameRead, 0);
            File file = new File(fileName);
            
            try {
                if (file.createNewFile()){
                    //System.out.println("File is created!");
                    file.setReadable(true);
                    file.setWritable(true);
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

    public static void ackTableInitialization(Parent dad){
        for (int i=0;i<=1;i++){
            dad.ackTable.put(i, 0);
        }

    }

    public void terminate() {
        System.exit(1);
    }

    // public static void skeleton(Parent dad) {

    //     Transport transport = new Transport(dad);
    //     Datalink datalink=new Datalink(dad);
    //     boolean llego=false;
    //     int counter=0;
        
    //     for (int i=0;i<=dad.end;i++){

    //         if (dad.isMsg){
    //             if (!llego) {
    //                 if ((counter%5)==0) {
    //                     transport.transport_send_string(dad.myID,dad.dstID,dad.msg);
    //                 }
    //             }                
    //         }

    //         datalink.datalink_receive_from_channel();

    //         if (dad.sb[0]==dad.ab[0]) {
    //             llego=true;
    //         }
    //         try {
    //             Thread.sleep(1000);
    //         } catch (InterruptedException ex) {
    //             System.out.println(ex.getStackTrace());
    //         }
    //         counter++;
    //     }
    // }

    public static void printreadOffset(Parent dad){
        Set<String> keys=dad.readOffset.keySet();

        for (String key:keys) {
            System.out.println("[Parent] key: "+key+", value:"+dad.readOffset.get(key));
        }
    }

    public void modAc(int index, int newAc) {
        this.ab[index]=newAc;
    }

    public static boolean isMyNeighbor(Parent dad) {
        boolean answer = false;

        String[] ngbs=dad.ngbs.split(",");

        for (String ngb:ngbs) {
            if (ngb.equals(dad.dstID)) {
                answer=true;
            } 
        }

        return answer;
    }

}