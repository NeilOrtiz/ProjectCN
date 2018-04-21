package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Reader {
    // private String pathname;
    // private Parent dad;
    private int count;

    public Reader(){
        // this.pathname=pathname;
        // this.dad=dad;
        this.count=0;
    }

    public String readFile(String pathname,Parent dad){

        String answer="null";
        try {
            String str = "SharedFile";
            BufferedReader ReadFile = new BufferedReader(new FileReader(pathname));
            int temp=0;
            //System.out.println("[readFile] 1");
            while ((str = ReadFile.readLine()) != null) {
                ++temp;
                //System.out.println("[readFile] pathname: "+pathname);
                if (temp > dad.readOffset.get(pathname)) {
                    // String filePath = "node"+dad.myID+"received"+".txt";
                    // BufferedWriter WriteFile = new BufferedWriter(new FileWriter(filePath,true));
                    // WriteFile.write(str);
                    // WriteFile.write("\r\n");
                    // WriteFile.close();
                    answer=str;
                }
            }
            dad.readOffset.replace(pathname, temp);
            ReadFile.close();
            
        } catch (Exception e) {
            System.out.println(e + " in readFile()");
        }

        return answer;
    }

}
