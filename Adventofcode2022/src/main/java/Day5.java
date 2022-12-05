import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day5 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        Day5 day4 = new Day5();
        day4.run();
    }

    public void run() throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Path.of(Day5.class.getClassLoader().getResource("day4.txt").toURI()));

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

        public Range() {
        }

        public Range(String rangeAsString) {
            String[] range = rangeAsString.split("-");
            this.from = Integer.parseInt(range[0]);
            this.to = Integer.parseInt(range[1]);
        }

        public Range(int from, int to) {
            this.from = from;
            this.to = to;
        }

        public boolean contain(Range other) {
            return from <= other.from && to >= other.to;
        }

        public boolean overlap(Range other) {
            return (from <= other.from && to >= other.from) || (other.from <= from && other.to >= to);
        }


//.234.....  2-4
//.....678.  6-8
//
//.23......  2-3
//...45....  4-5
//
//....567..  5-7
//......789  7-9
//
//.2345678.  2-8
//..34567..  3-7
//
//.....6...  6-6
//...456...  4-6
//
//.23456...  2-6
//...45678.  4-8
    }
}
