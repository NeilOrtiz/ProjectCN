package network;

public class Datalink {
    private Parent dad;

    public Datalink (Parent dad) {
        this.dad=dad;
    }

    public void datalink_receive_from_network(byte[] msg, int len, int next_hop) {

    }
}