import java.util.*;

public class Mapper <K,V> {
    private List<Pair<K,V>> pairs;
    public Mapper() {
        pairs = new ArrayList<>();
    }

    public void addPairs(Pair<K,V> pair){
        pairs.add(pair);
    }

    public void setPairs(List<Pair<K, V>> pairs) {
        this.pairs = pairs;
    }
    public List<Pair<K,V>> getPairs(){
        return this.pairs;
    }

    @Override
    public String toString() {
        return this.pairs.toString();
    }

}

