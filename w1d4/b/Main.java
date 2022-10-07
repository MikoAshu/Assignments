package w1_ass2.b;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String inputSplit0 = "Art is beautiful and life enhancing. However it pays very little. Many artists have a hard life.";
        String inputSplit1 = "Sun is there every day. Moon comes every day. Let us live every day as the best day so far.";
        String inputSplit2 = "Meditation is very important for the development of consciousness. So let us meditate every day.";
        String inputSplit3 = "Earth is blue if you look from outer space. Mars is red. Moon is yellow. Sun is white. What a wonderful world.";
//        String inputSplit4 = "Addison ate a red apple";

        List<String> inputSplits = new ArrayList<>();
        inputSplits.add(inputSplit0);
        inputSplits.add(inputSplit1);
        inputSplits.add(inputSplit2);
        inputSplits.add(inputSplit3);
//        inputSplits.add(inputSplit4);
        new WordCharAvg(3, inputSplits);
    }
}
