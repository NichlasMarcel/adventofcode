import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        Day6 run = new Day6();
        run.run();
    }

    public void run() throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Path.of(Day6.class.getClassLoader().getResource("day6.txt").toURI()));

        // Init map
        char[] chars = lines.get(0).toCharArray();

        // Task 1
        findDistinctSequenceIndex(4, chars);

        // Task 2
        findDistinctSequenceIndex(14, chars);
    }

    private static void findDistinctSequenceIndex(int sequenceLength, char[] chars) {
        for(int i = sequenceLength - 1; i < chars.length; i++) {
            Set<Character> set = new HashSet<>();
            for(int j = i; (i - (sequenceLength - 1) <= j); j--) {
                set.add(chars[j]);
            }

            if(set.size() == sequenceLength) {
                System.out.println(i+1);
                break;
            }
        }
    }
}
