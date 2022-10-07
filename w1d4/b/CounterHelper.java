package w1_ass2.b;

public class CounterHelper {
    int count;
    int sum;

    public CounterHelper(int count, int sum) {
        this.count = count;
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "[ " + sum +
                ", " + count +
                " ]";
    }
}
