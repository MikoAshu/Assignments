package w1_ass2;

import java.util.Comparator;

public class StringPairComparator <V> implements Comparator<Pair<String, V>> {
    @Override
    public int compare(Pair<String, V> o1, Pair<String, V> o2) {
        return o1.getKey().compareTo(o2.getKey());
    }
}
