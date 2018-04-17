package network;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Writer {
    private int count;
    private String pathname;

    public Writer(String pathname) {
        this.pathname=pathname;
        this.count=0;

        try {
            File channel = new File(pathname);
            FileWriter channelFile = new FileWriter(channel);
            channelFile.close();
        } catch (Exception e) {
            System.err.println(e+" in writer");
        }
    }

    public void writeFile() {

        try {
            String str="Count " + count;
            BufferedWriter WriteFile = new BufferedWriter(new FileWriter(this.pathname,true));
            WriteFile.write(str);
            WriteFile.write("\n");
            WriteFile.close();
            this.count++;
        } catch (Exception e) {
            System.err.println(e+" in writer");
        }

    }
}