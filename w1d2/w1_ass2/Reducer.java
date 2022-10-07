package w1_ass2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Reducer {
    private final List<Pair<String, List<Integer>>> reducerInput;

    private final List<Pair<String, Integer>> reducerOutput;
    public Reducer() {
        reducerOutput = new ArrayList<>();
        reducerInput = new ArrayList<>();
    }

    public void addReducerInput(Pair<String, List<Integer>> pair){
        this.reducerInput.add(pair);
    }

    public List<Pair<String, Integer>> getReducerOutput(){
        return this.reducerOutput;
    }

    public List<Pair<String, List<Integer>>> getReducerInput() {
        return reducerInput;
    }

    public void reduce() {
        reducerInput.forEach(o-> {
            Optional<Integer> v = o.getValue().stream().reduce(Integer::sum);
            if (v.isEmpty()) return;
            Pair<String, Integer> pair = new Pair<>(o.getKey(), v.get());
            reducerOutput.add(pair);
        });
    }

    @Override
    public String toString() {
        return this.reducerOutput.toString();
    }
}
