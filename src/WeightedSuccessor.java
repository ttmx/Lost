public class WeightedSuccessor {

    final byte i;
    final byte j;
    final int weight;

    public WeightedSuccessor(byte i, byte j, int weight) {
        this.i = i;
        this.j = j;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeightedSuccessor that = (WeightedSuccessor) o;

        if (i != that.i) return false;
        return j == that.j;
    }

    @Override
    public int hashCode() {
        int result = i;
        result = 31 * result + (int) j;
        return result;
    }
}
