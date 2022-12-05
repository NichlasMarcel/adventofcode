import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.temporal.ValueRange;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day4 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        Day4 day4 = new Day4();
        day4.run();
    }

    public void run() throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Path.of(Day4.class.getClassLoader().getResource("day4.txt").toURI()));

        // Task 1
        System.out.println(
                lines.stream().map(p -> {
                    String[] assignmentPair = p.split(",");
                    return List.of(new Range(assignmentPair[0]), new Range(assignmentPair[1]));
                }).filter(r -> r.get(0).contain(r.get(1)) || r.get(1).contain(r.get(0))).count()
        );

        // Task 2
        System.out.println(
                lines.stream().map(p -> {
                    String[] assignmentPair = p.split(",");
                    return List.of(new Range(assignmentPair[0]), new Range(assignmentPair[1]));
                }).filter(r -> r.get(0).overlap(r.get(1)) || r.get(1).overlap(r.get(0))).count()
        );
    }

    public class Range {
        private int from;
        private int to;

        public Range(String rangeAsString) {
            String[] range = rangeAsString.split("-");
            this.from = Integer.parseInt(range[0]);
            this.to = Integer.parseInt(range[1]);
        }

        public boolean contain(Range other) {
            return from <= other.from && to >= other.to;
        }

        public boolean overlap(Range other) {
            return (from <= other.from && to >= other.from) || (other.from <= from && other.to >= to);
        }
    }
}
