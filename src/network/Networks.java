package network;

public class Networks {
    private Parent dad;


    public Networks (Parent dad) {
        this.dad=dad;
    }

    public void network_receive_from_transport(char[] msg,int len, int dest){
        //TODO: Networks.network_receive_from_transport()
        for (char x:msg) {
            System.out.print(x);
        }
        System.out.println("    Length msg: "+len);
    }
}