import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Mapper<String, Integer> mapper = new Mapper<>();
        try {

            Path sourcePath = Path.of("testDataForW1D1.txt").toAbsolutePath();
            Path outputPath = Paths.get("output.txt");

            // create the file if it does not exist
            Files.writeString(outputPath, "" , StandardOpenOption.CREATE);

            String inputString = Files.readString(sourcePath);
            List<Character> endTokens = Arrays.asList('.','?',';', ':', ',', '!') ;
            Set<Character> tokenSet = new HashSet<>(endTokens);

            mapper.setPairs(
                    Arrays.stream(inputString.split("\\s"))
                            .map(String::toLowerCase)
                            .filter(o -> o.length() > 0)
                            .map(o -> {
                                int n = o.length() -1;
                                char c = o.charAt(n);
                                if(tokenSet.contains(c)) {
                                    return o.substring(0, n);
                                }
                                return o;
                            })
                            .peek(System.out::println)
                            .filter(o -> o.matches("[a-z-]+$"))
                            .flatMap(o -> Arrays.stream(o.split("-")))
                            .map(o->  new Pair<>(o, 1))
                            .sorted((o1, o2) -> new StringPairComparator().compare(o1,o2)).toList()
            );

            // clear the file
            Files.writeString(outputPath, "" , StandardOpenOption.TRUNCATE_EXISTING);

            mapper.getPairs()
                    .stream()
                    .peek(o -> {
                        try {
                            Files.writeString(outputPath, o.toString() + "\n", StandardOpenOption.APPEND );
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
