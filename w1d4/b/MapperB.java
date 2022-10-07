package w1_ass2.b;

import w1_ass2.Pair;

import java.util.*;
import java.util.stream.Stream;

public class MapperB {
    private Map<String, Pair<String, CounterHelper>> pairsMap;
    private List<Pair<String, CounterHelper>> pairs;
    private String inputSplit;
    public MapperB(String inputSplit) {
        this.inputSplit = inputSplit;
        pairs = new ArrayList<>();
        pairsMap = new HashMap<>();
    }

    public void mapTasks(){
        Arrays.stream(inputSplit.split("\\s\\.\\s"))
                .flatMap(o -> Arrays.stream(o.split("\\s")))
                .map(String::toLowerCase)
                .filter(o -> o.length() > 0)
                .peek(this::map)
                .toList();
        pairs.addAll(pairsMap.values());
    }

    public List<Pair<String, CounterHelper>> getPairs(){
        return this.pairs;
    }

    public void map(String record){
        List<Character> endTokens = Arrays.asList('.','?',';', ':', ',', '!', '\"', '\'') ;
        List<Character> startTokens = Arrays.asList('\"', '\'') ;

        Set<Character> endTokenSet = new HashSet<>(endTokens);
        Set<Character> startTokenSet = new HashSet<>(startTokens);

        Stream.of(record)
                .map(o -> {
                    int end = o.length() - 1;
                    int start = 0;
                    char c1 = o.charAt(start);
                    char c2 = o.charAt(end);

                    if(startTokenSet.contains(c1)) {
                        start++;
                    }
                    if(endTokenSet.contains(c2)) {
                        end--;
                    }
                    return o.substring(start, end+1);
                })
                .filter(o -> o.matches("[a-z-]+$"))
                .flatMap(o -> Arrays.stream(o.split("-")))
                .peek(o-> {
                    String key = String.valueOf(o.charAt(0));
                    if (!pairsMap.containsKey(key)) {
                        pairsMap.put(key, new Pair<>(key, new CounterHelper(1, o.length())));
                    } else {
                        Pair<String, CounterHelper> pair = pairsMap.get(key);
                        CounterHelper counterHelper = pair.getValue();
                        counterHelper.count++;
                        counterHelper.sum+= o.length();

                        pair.setValue(counterHelper);

                        pairsMap.put(key, pair);
                    }
                })
                .toList();
    }

    @Override
    public String toString() {
        return this.pairs.toString();
    }

}

