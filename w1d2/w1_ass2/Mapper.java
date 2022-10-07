package w1_ass2;

import java.util.*;
import java.util.stream.Stream;

public class Mapper {
    private List<Pair<String, Integer>> pairs;
    private String inputSplit;
    public Mapper(String inputSplit) {
        this.inputSplit = inputSplit;
        pairs = new ArrayList<>();
    }

    public void mapTasks(){
        Arrays.stream(inputSplit.split("\\s\\.\\s"))
                .flatMap(o -> Arrays.stream(o.split("\\s")))
                .map(String::toLowerCase)
                .filter(o -> o.length() > 0)
                .peek(this::map)
                .toList();
    }

    public void addPairs(Pair<String, Integer> pair){
        pairs.add(pair);
    }
    public void setPairs(List<Pair<String, Integer>> pairs) {
        this.pairs = pairs;
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
                .map(o->  new Pair<>(o, 1))
                .peek(pairs::add)
                .toList();
    }

    @Override
    public String toString() {
        return this.pairs.toString();
    }

}

