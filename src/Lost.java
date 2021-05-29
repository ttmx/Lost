import java.util.*;

public class Lost {

    private final List<WeightedSuccessor>[][] kateGraph;
    private final List<Edge> johnGraph;
    private final byte[] johnStart;
    private final byte[] kateStart;
    private final byte[] exit;
    private final short kateVertices;
    private final short johnVertices;

    public Lost(List<WeightedSuccessor>[][] kateGraph, List<Edge> johnGraph, byte[] kateStart, byte[] johnStart, byte[] exit, short kateVertices, short johnVertices) {
        this.kateGraph = kateGraph;
        this.johnGraph = johnGraph;
        this.johnStart = johnStart;
        this.kateStart = kateStart;
        this.exit = exit;
        this.kateVertices = kateVertices;
        this.johnVertices = johnVertices;
    }

    public String result() {
        return calcJohn() + "\n" + perilousLocations2();
    }

    public String perilousLocations2() {
        if (Arrays.equals(kateStart, exit)) {
            return "Kate 0";
        }
        int kateLength = dijkstra();
        return (kateLength != Integer.MAX_VALUE) ? "Kate " + kateLength : "Kate Unreachable";
    }

    private int dijkstra() {
        int[][] length = new int[kateGraph.length][kateGraph[0].length];
        boolean[][] selected = new boolean[kateGraph.length][kateGraph[0].length];
        PriorityQueue<WeightedSuccessor> connected = new PriorityQueue<>(kateVertices, Comparator.comparingInt(o -> o.weight));
        for (int[] ints : length) {
            Arrays.fill(ints, Integer.MAX_VALUE);
        }
        connected.add(new WeightedSuccessor(kateStart[0], kateStart[1], (byte) 0));
        length[kateStart[0]][kateStart[1]] = 0;
        do {
            WeightedSuccessor node = connected.poll();
            selected[node.i][node.j] = true;
            if (kateGraph[node.i][node.j] != null) {
                for (WeightedSuccessor v : kateGraph[node.i][node.j]) {
                    if (!selected[v.i][v.j]) {
                        int newLength = length[node.i][node.j] + v.weight;
                        if (newLength < length[v.i][v.j]) {
                            length[v.i][v.j] = newLength;
                            boolean nodeIsInQueue = length[v.i][v.j] < Integer.MAX_VALUE;
                            if (nodeIsInQueue) {
                                connected.remove(new WeightedSuccessor(v.i, v.j, 0));
                            }
                            connected.add(new WeightedSuccessor(v.i, v.j, length[v.i][v.j]));
                        }
                    }
                }
            }
        } while (!connected.isEmpty());
        return length[exit[0]][exit[1]];
    }

    public String perilousLocations() {
        byte[][] processed = new byte[kateGraph.length][kateGraph[0].length];
        int kateLength = bfs(processed, new WeightedSuccessor(kateStart[0], kateStart[1], (byte) 0));
        return (kateLength != -1) ? "Kate " + kateLength : "Kate Unreachable";
    }

    private int bfs(byte[][] processed, WeightedSuccessor current) {
        @SuppressWarnings("unchecked") Queue<WeightedSuccessor>[] borders = (Queue<WeightedSuccessor>[]) new Queue[2];
        for (int i = 0; i < borders.length; i++) {
            borders[i] = new LinkedList<>();
        }
        int currentBorder = 0;
        Queue<WeightedSuccessor> border = borders[currentBorder];
        border.add(current);
        processed[current.i][current.j] = 0;
        int[] lastBorderSizes = new int[2];
        lastBorderSizes[currentBorder] = border.size();
        short currentDepth = 0;
        do {
            do {
                current = border.remove();
                lastBorderSizes[currentBorder]--;
                for (WeightedSuccessor node : kateGraph[current.i][current.j]) {
                    if (node.i == exit[0] && node.j == exit[1]) {
                        return currentDepth + node.weight;
                    }
                    switch (processed[node.i][node.j]) {
                        case 0:
                            borders[node.weight - 1].add(node);
                            processed[node.i][node.j] = (byte) node.weight;
                            if (node.weight - 1 != currentBorder) {
                                lastBorderSizes[node.weight - 1]++;
                            }
                            break;
                        case 2:
                            if (node.weight == 1) {
                                borders[0].add(node);
                                processed[node.i][node.j] = (byte) node.weight;
                                borders[1].remove(node); //got to make this fast
                            }
                            break;
                        default:
                            break;
                    }
                }
            } while (lastBorderSizes[currentBorder] != 0);
            lastBorderSizes[currentBorder] = border.size();
            for (int i = 0; i < borders.length; i++) {
                currentDepth++;
                if (!borders[i].isEmpty()) {
                    border = borders[i];
                    currentBorder = i;
                    break;
                }
            }
        } while (!border.isEmpty());
        return -1;
    }

    public String calcJohn() {
        if (Arrays.equals(johnStart, exit)) {
            return "John 0";
        }
        int[][] length = new int[kateGraph.length][kateGraph[0].length];
        for (int[] ints : length)
            Arrays.fill(ints, Integer.MAX_VALUE);

        length[johnStart[0]][johnStart[1]] = 0;
        boolean changes = true;
        for (int i = 1; i < johnVertices && changes; i++) {
            changes = updateLengths(length);
        }
        if (changes && updateLengths(length))
            return "John Lost in Time";

        int distance = length[exit[0]][exit[1]];
        return (distance == Integer.MAX_VALUE) ? "John Unreachable" : "John " + distance;
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
