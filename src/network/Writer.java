package network;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Writer {
    private String pathname;

    public Writer(String pathname) {
        this.pathname=pathname;
    }

    public void writeFile(String type, int channel, String secNum, String msg) {

        try {
            String str=type+" "+channel+" "+secNum+" "+msg;
            BufferedWriter WriteFile = new BufferedWriter(new FileWriter(this.pathname,true));
            WriteFile.write("\r\n");
            WriteFile.write(str);
            WriteFile.close();
        } catch (Exception e) {
            System.err.println(e+" in writer");
        }

    }
}