import java.util.LinkedList;
import java.util.List;

public class LostBuilder {

    private static final byte
            W = (byte) 'W',
            G = (byte) 'G',
            O = (byte) 'O',
            X = (byte) 'X';

    private enum Directions {
        TOP((byte) -1, (byte) 0),
        LEFT((byte) 0, (byte) -1);

        byte vertical, horizontal;

        Directions(byte vertical, byte horizontal) {
            this.vertical = vertical;
            this.horizontal = horizontal;
        }
    }

    private final byte[][] grid;
    private final List<WeightedSuccessor>[][] kateGraph;
    private final List<Edge> johnGraph;
    private final byte[] johnStart;
    private final byte[] kateStart;
    private final byte[] magicalCells;
    private final byte[] exit;

    @SuppressWarnings("unchecked")
    public LostBuilder(byte[][] grid, byte rows, byte columns, byte numMagicalCells) {
        this.grid = grid;
        this.johnGraph = new LinkedList<>();
        this.kateGraph = (List<WeightedSuccessor>[][]) new List[rows][columns];
        this.johnStart = new byte[2];
        this.kateStart = new byte[2];
        this.magicalCells = new byte[2 * numMagicalCells];
        this.exit = new byte[2];
    }

    public Lost build() {
        //TODO
        return new Lost(kateGraph, johnGraph,kateStart,johnStart,exit);
    }

    public void processCell(byte i, byte j) {
        byte cell = grid[i][j];
        switch (cell) {
            case X:
                exit[0] = i;
                exit[1] = j;
                //can optimise
            case G:
                //john
                for (Directions dir : Directions.values()) {
                    byte i1 = (byte) (i + dir.vertical);
                    byte j1 = (byte) (j + dir.horizontal);
                    if (inBounds(i1, j1) && johnCanMoveTo(grid[i1][j1])) {
                        johnGraph.add(new Edge(i, j, i1, j1, weightFrom(cell)));
                        johnGraph.add(new Edge(i1, j1, i, j, weightFrom(grid[i1][j1])));
                    }
                }
            case W:
                //kate
                for (Directions dir : Directions.values()) {
                    byte i1 = (byte) (i + dir.vertical);
                    byte j1 = (byte) (j + dir.horizontal);
                    if (inBounds(i1, j1) && kateCanMoveTo(grid[i1][j1])) {
                        List<WeightedSuccessor> origin = kateGraph[i][j];
                        if (origin == null) {
                            kateGraph[i][j] = origin = new LinkedList<>();
                        }
                        origin.add(new WeightedSuccessor(i1, j1, weightFrom(cell)));
                        List<WeightedSuccessor> destiny = kateGraph[i1][j1];
                        if (destiny == null) {
                            kateGraph[i1][j1] = destiny = new LinkedList<>();
                        }
                        destiny.add(new WeightedSuccessor(i, j, weightFrom(grid[i1][j1])));
                    }
                }
                break;
            case O:
                break;
            default:
                assert ((byte) '1' <= cell && cell <= (byte) '9');
                magicalCells[cell - '1'] = i;
                magicalCells[cell - '1' + 1] = j;
                grid[i][j] = G;
                processCell(i, j);
                break;
        }
    }

    public void addMagicalEdge(int magicalCell, byte i, byte j, int weight) {
        johnGraph.add(new Edge(magicalCells[2 * magicalCell], magicalCells[2 * magicalCell + 1], i, j, weight));
    }

    public void setJohnStart(byte i, byte j) {
        johnStart[0] = i;
        johnStart[1] = j;
    }

    public void setKateStart(byte i, byte j) {
        kateStart[0] = i;
        kateStart[1] = j;
    }

    private boolean kateCanMoveTo(byte cell) {
        return cell != O;
    }

    private boolean johnCanMoveTo(byte cell) {
        switch (cell) {
            case X:
            case G:
            default:
                return true;
            case W:
            case O:
                return false;
        }
    }

    private byte weightFrom(byte cell) {
        switch (cell) {
            case X:
            case G:
            default:
                return 1;
            case W:
                return 2;
            case O:
                return -1;
        }
    }

    private boolean inBounds(int i, int j) {
        return i < grid.length && i >= 0 &&
                j < grid[0].length && j >= 0;
    }
}
