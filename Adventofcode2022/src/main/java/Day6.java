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

        // Task 1
        findDistinctSequenceIndexMethod2(lines.get(0), 4);

        // Task 2
        findDistinctSequenceIndexMethod2(lines.get(0), 14);
    }

    private static void findDistinctSequenceIndex(int sequenceLength, char[] chars) {
        for (int i = sequenceLength - 1; i < chars.length; i++) {
            Set<Character> set = new HashSet<>();
            for (int j = i; (i - (sequenceLength - 1) <= j); j--) {
                set.add(chars[j]);
            }

            if (set.size() == sequenceLength) {
                System.out.println(i + 1);
                break;
            }
        }
    }

    private static void findDistinctSequenceIndexMethod2(String line, int sequenceLength) {
        for (int i = sequenceLength; i < line.length(); i++) {
            if (Arrays.stream(line.substring(i - sequenceLength, i).split("")).distinct().count() == sequenceLength) {
                System.out.println(i);
                break;
            }
        }
    }
}
