package w1_ass2.a;

import w1_ass2.Pair;

import java.util.*;
import java.util.stream.Stream;

public class MapperA {
    private Map<String, Pair<String, Integer>> pairsMap;
    private List<Pair<String, Integer>> pairs;
    private String inputSplit;
    public MapperA(String inputSplit) {
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

    public List<Pair<String, Integer>> getPairs(){
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
                    if (!pairsMap.containsKey(o)) {
                        pairsMap.put(o, new Pair<>(o, 1));
                    } else {
                        Pair<String, Integer> pair = pairsMap.get(o);
                        pair.setValue(pair.getValue()+1);
                        pairsMap.put(o, pair);
                    }
                })
                .toList();
    }

    @Override
    public String toString() {
        return this.pairs.toString();
    }

}

