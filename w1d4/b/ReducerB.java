package w1_ass2.b;

import w1_ass2.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReducerB {
    private final List<Pair<String, List<CounterHelper>>> reducerInput;

    private final List<Pair<String, Double>> reducerOutput;
    public ReducerB() {
        reducerOutput = new ArrayList<>();
        reducerInput = new ArrayList<>();
    }

    public void addReducerInput(Pair<String, List<CounterHelper>> pair){
        this.reducerInput.add(pair);
    }

    public List<Pair<String, Double>> getReducerOutput(){
        return this.reducerOutput;
    }

    public List<Pair<String, List<CounterHelper>>> getReducerInput() {
        return reducerInput;
    }

    public void reduce() {

        reducerInput.forEach(o-> {

            Optional<CounterHelper> v = o.getValue().stream().reduce((m, n) -> {
                m.sum += n.sum;
                m.count += n.count;
                return m;
            });
            if (v.isEmpty()) return;
            CounterHelper res = v.get();
            double avg = (double) res.sum / res.count;

            Pair<String, Double> pair = new Pair<>(o.getKey(), avg);
            reducerOutput.add(pair);
        });
    }

    @Override
    public String toString() {
        return this.reducerOutput.toString();
    }
}
