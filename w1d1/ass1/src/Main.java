import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Main {
    private static final String MAPPER_OUTPUT_PREFIX = "mapper_output";
    private static final String OUTPUT_SUFFIX = ".txt";
    private static final String OUT_DIR = "src/outputs/";
    private static final String INPUT_FILE = "src/input/testDataForW1D1.txt";
    private static List<Pair<String, Integer>> mapperOutput;

    public static void main(String[] args) {
        try {
            Path sourcePath = Path.of(INPUT_FILE).toAbsolutePath();
//            Path outputPath = Paths.get("w1_ass2/a/inputs/w1d1_output.txt");

            // create the file if it does not exist
//            Files.writeString(outputPath, "" , StandardOpenOption.CREATE);

            String inputSplit = Files.readString(sourcePath);
            mapperOutput = new ArrayList<>();
            Mapper mapper = new Mapper(inputSplit);
            mapper.mapTasks();
            mapperOutput.addAll(mapper.getPairs());
            mapperOutput.sort((o1, o2) -> new StringPairComparator().compare(o1, o2));
            writeFile(MAPPER_OUTPUT_PREFIX +  OUTPUT_SUFFIX, mapperOutput);


        } catch (IOException | ClassCastException e) {
            throw new RuntimeException(e);
        }
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
