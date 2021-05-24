public class Edge {

    final byte iTail;
    final byte jTail;
    final byte iHead;
    final byte jHead;
    final int weight;

    public Edge(byte iTail, byte jTail, byte iHead, byte jHead, int weight) {
        this.iTail = iTail;
        this.jTail = jTail;
        this.iHead = iHead;
        this.jHead = jHead;
        this.weight = weight;
    }
}
