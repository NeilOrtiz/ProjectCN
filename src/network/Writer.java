package network;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Writer {
  

    public Writer() {

    }

    //public void writeFile(String str, int channel, String secNum, String msg) {
    public void writeFile(String str,String pathname) {

        try {
            //String str=type+" "+channel+" "+secNum+" "+msg;
            BufferedWriter WriteFile = new BufferedWriter(new FileWriter(pathname,true));
            WriteFile.write("\r\n");
            WriteFile.write(str);
            WriteFile.close();
        } catch (Exception e) {
            System.err.println(e+" in writer");
        }

    }
}