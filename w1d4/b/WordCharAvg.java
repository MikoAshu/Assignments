package w1_ass2.b;

import w1_ass2.Pair;
import w1_ass2.StringPairComparator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class WordCharAvg {
    List<MapperB> mappers;
    List<ReducerB> reducers;
    private List<Pair<String, CounterHelper>> mapperOutputs;

    private static final String MAPPER_OUTPUT_PREFIX = "mapper_output_";
    private static final String REDUCER_OUTPUT_PREFIX = "reducer_output_";
    private static final String REDUCER_INPUT_PREFIX = "reducer_input_";
    private static final String OUTPUT_SUFFIX = ".txt";
    private static final String mapper_out_file = "mapper_output.txt";
    private static final String OUT_DIR = "b/outputs/";

    WordCharAvg(int reducerCount, List<String> inputSplits){
        reducers = new ArrayList<>();
        mapperOutputs= new ArrayList<>();
        for (int i = 0; i < reducerCount; i++) {
            ReducerB reducer = new ReducerB();
            reducers.add(reducer);
        }

        mappers = new ArrayList<>();
        for (int i = 0; i < inputSplits.size(); i++) {
            MapperB mapper = new MapperB(inputSplits.get(i));
            mappers.add(mapper);
            mapper.mapTasks();
            writeFile(MAPPER_OUTPUT_PREFIX + i + OUTPUT_SUFFIX, mapper.getPairs());
            mapperOutputs.addAll(mapper.getPairs());
        }

        assert mapperOutputs != null;
        mapperOutputs.sort((o1, o2) -> new StringPairComparator().compare(o1, o2));
        writeFile(mapper_out_file, mapperOutputs);

        generateReducerInput();

        for (int i = 0; i < reducers.size(); i++) {
            ReducerB reducer = reducers.get(i);
            writeFile(REDUCER_INPUT_PREFIX + i + OUTPUT_SUFFIX, reducer.getReducerInput());
            reducer.reduce();
            writeFile(REDUCER_OUTPUT_PREFIX + i + OUTPUT_SUFFIX, reducer.getReducerOutput());
        }
    }

    private void generateReducerInput() {
        final Pair<String, List<CounterHelper>>[] lastGroupPair = new Pair[]{null};
        mapperOutputs.forEach(o -> {
            shuffleSort(lastGroupPair, o);
        });
    }

    private void shuffleSort(Pair<String, List<CounterHelper>>[] lastGroupPair, Pair<String, CounterHelper> pair) {
        List<CounterHelper> v = new ArrayList<>();
        String k = pair.getKey();
        Pair<String, List<CounterHelper>> groupByPair = new Pair<>(k,v);
        if (lastGroupPair[0] != null && lastGroupPair[0].getKey().equals(k)){
            groupByPair = lastGroupPair[0];
            v = groupByPair.getValue();
        } else {
            lastGroupPair[0] = groupByPair;
            int partition = getPartition(k, reducers.size());
            reducers.get(partition).addReducerInput(groupByPair);
        }
        v.add(pair.getValue());
    }

    static int getPartition(String k, int numReducers) {
        return Math.abs(k.hashCode()) % numReducers;
    }

    public static <T> void  writeFile(String fileName, List<T> list ) {
        Path outputPath = Paths.get(OUT_DIR + fileName);
        try {
            // create the file if it does not exist
            Files.writeString(outputPath, "" , StandardOpenOption.CREATE);
            Files.writeString(outputPath, "" , StandardOpenOption.TRUNCATE_EXISTING);
            list.forEach(o -> {
                try {
                    Files.writeString(outputPath, o.toString(), StandardOpenOption.APPEND );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
