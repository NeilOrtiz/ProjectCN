package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Reader {
    private String pathname;
    private Parent dad;
    private int count;

    public Reader(String pathname,Parent dad){
        this.pathname=pathname;
        this.dad=dad;
        this.count=0;
    }

    public void readFile(){

        try {
            String str = "SharedFile";
            BufferedReader ReadFile = new BufferedReader(new FileReader(this.pathname));
            int temp=0;

            while ((str = ReadFile.readLine()) != null) {
                ++temp;
                if (temp > count) {
                    String filePath = "node"+dad.myID+"received";
                    BufferedWriter WriteFile = new BufferedWriter(new FileWriter(filePath,true));
                    WriteFile.write(str);
                    WriteFile.write("\n");
                    WriteFile.close();
                }
            }
            ReadFile.close();
            
        } catch (Exception e) {
            System.out.println(e + " in readFile()");
        }
    }

}
