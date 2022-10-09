import w1_ass2.Mapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;

public class W1D1_Main {
    public static void main(String[] args) {

        try {

            Path sourcePath = Path.of("sample/testDataForW1D1.txt").toAbsolutePath();
            Path outputPath = Paths.get("w1_ass2/a/inputs/w1d1_output.txt");

            // create the file if it does not exist
            Files.writeString(outputPath, "" , StandardOpenOption.CREATE);

            String inputSplit = Files.readString(sourcePath);

            Mapper mapper = new Mapper(inputSplit);
            mapper.mapTasks();

            // clear the file
            Files.writeString(outputPath, "" , StandardOpenOption.TRUNCATE_EXISTING);

            mapper.getPairs()
                    .stream()
                    .peek(o -> {
                        try {
                            Files.writeString(outputPath, o.toString(), StandardOpenOption.APPEND );
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
