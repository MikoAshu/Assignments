package w1_ass2.a;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String inputSplit0 = "\"cat bat\" mat-pat mum.edu sat.\n" +
                "fat 'rat eat cat' mum_cs mat";
        String inputSplit1 = "bat-hat mat pat \"oat\n" +
                "hat rat mum_cs eat oat-pat";
        String inputSplit2 = "zat lat-cat pat jat.\n" +
                "hat rat. kat sat wat";

        List<String> inputSplits = new ArrayList<>();
        inputSplits.add(inputSplit0);
        inputSplits.add(inputSplit1);
        inputSplits.add(inputSplit2);
        new InMapperWordCount(4, inputSplits);
    }
}
