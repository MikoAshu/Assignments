package w1_ass2;

import java.util.Comparator;

public class StringPairComparator implements Comparator<Pair<String, Integer>> {
    @Override
    public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
        return o1.getKey().compareTo(o2.getKey());
    }
}
