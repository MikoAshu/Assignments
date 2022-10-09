package w1_ass2.a;

import w1_ass2.Mapper;
import w1_ass2.Pair;
import w1_ass2.Reducer;
import w1_ass2.StringPairComparator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Main {
    private static final String MAPPER_OUTPUT_PREFIX = "mapper_output";
    private static final String REDUCER_OUTPUT_PREFIX = "reducer_output";
    private static final String REDUCER_INPUT_PREFIX = "reducer_input";
    private static final String OUTPUT_SUFFIX = ".txt";
    private static final String OUT_DIR = "w1_ass2/a/outputs/";
    private static List<Pair<String, Integer>> mapperOutput;

    public static void main(String[] args) {
        try {
            Path sourcePath = Path.of("sample/testDataForW1D1.txt").toAbsolutePath();
            Path outputPath = Paths.get("w1_ass2/a/inputs/w1d1_output.txt");

            // create the file if it does not exist
            Files.writeString(outputPath, "" , StandardOpenOption.CREATE);

            String inputSplit = Files.readString(sourcePath);
            mapperOutput = new ArrayList<>();
            Mapper mapper = new Mapper(inputSplit);
            mapper.mapTasks();
            mapperOutput.addAll(mapper.getPairs());
            mapperOutput.sort((o1, o2) -> new StringPairComparator().compare(o1, o2));
            writeFile(MAPPER_OUTPUT_PREFIX +  OUTPUT_SUFFIX, mapperOutput);

            Reducer reducer = new Reducer();
            generateReducerInput(reducer);

            writeFile(REDUCER_INPUT_PREFIX +  OUTPUT_SUFFIX, reducer.getReducerInput());
            reducer.reduce();

            writeFile(REDUCER_OUTPUT_PREFIX +  OUTPUT_SUFFIX, reducer.getReducerOutput());

        } catch (IOException | ClassCastException e) {
            throw new RuntimeException(e);
        }
    }

    private static void generateReducerInput(Reducer reducer) throws IOException {
        final Pair<String, List<Integer>>[] lastGroupPair = new Pair[]{null};
        mapperOutput.forEach(o -> {
            shuffleSort(reducer, lastGroupPair, o);
        });
    }

    private static void shuffleSort(Reducer reducer, Pair<String, List<Integer>>[] lastGroupPair, Pair<String, Integer> pair) {
        List<Integer> v = new ArrayList<>();
        String k = pair.getKey();
        Pair<String, List<Integer>> groupByPair = new Pair<>(k,v);
        if (lastGroupPair[0] != null && lastGroupPair[0].getKey().equals(k)){
            groupByPair = lastGroupPair[0];
            v = groupByPair.getValue();
        } else {
            lastGroupPair[0] = groupByPair;
            reducer.addReducerInput(groupByPair);
        }
        v.add(pair.getValue());
    }

    public static <T> void  writeFile(String fileName, List<T> list ) {
        Path outputPath = Paths.get(OUT_DIR + fileName);
        try {
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
