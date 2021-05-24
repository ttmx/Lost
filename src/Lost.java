import java.util.List;

public class Lost {

    private final List<WeightedSuccessor>[][] kateGraph;
    private final List<Edge> johnGraph;

    public Lost(List<WeightedSuccessor>[][] kateGraph, List<Edge> johnGraph) {
        this.kateGraph = kateGraph;
        this.johnGraph = johnGraph;
    }

    public String result() {
        return null;
    }
}
