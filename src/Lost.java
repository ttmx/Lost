import java.util.Arrays;
import java.util.List;

public class Lost {

    private final List<WeightedSuccessor>[][] kateGraph;
    private final List<Edge> johnGraph;
    private final byte[] johnStart;
    private final byte[] kateStart;
    private final byte[] exit;

    public Lost(List<WeightedSuccessor>[][] kateGraph, List<Edge> johnGraph, byte[] kateStart, byte[] johnStart, byte[] exit) {
        this.kateGraph = kateGraph;
        this.johnGraph = johnGraph;
        this.johnStart = johnStart;
        this.kateStart = kateStart;
        this.exit = exit;
    }

    public String result() {
        return calcJohn();
    }

    public String calcJohn() {
        int[][] length = new int[kateGraph.length][kateGraph[0].length];
        for (int[] ints : length)
            Arrays.fill(ints,Integer.MAX_VALUE);

        length[johnStart[0]][johnStart[1]] = 0;
        boolean changes = true;
        for (int i = 1; i < kateGraph.length*kateGraph[0].length && changes; i++) {
            changes = updateLengths(length);
        }
        if(changes && updateLengths(length))
            return "John Lost in Time";

        final int distance = length[exit[0]][exit[1]];
        return (distance == Integer.MAX_VALUE)? "John Unreachable":"John " + distance;
    }

    boolean updateLengths(int[][] len) {
        boolean changes = false;
        for (Edge edge : johnGraph) {
            int iTail = edge.iTail;
            int jTail = edge.jTail;
            int iHead = edge.iHead;
            int jHead = edge.jHead;
            if (len[iTail][jTail] < Integer.MAX_VALUE) {
                int newLen = len[iTail][jTail] + edge.weight;
                if (newLen < len[iHead][jHead]) {
                    len[iHead][jHead] = newLen;
                    changes = true;
                }
            }
        }
        return changes;
    }


}
