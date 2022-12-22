import org.json.JSONArray;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day13Retry {

    public static void main(String[] args) throws URISyntaxException, IOException {
        Day13Retry run = new Day13Retry();
        run.run();
    }

    public void run() throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Path.of(Day13Retry.class.getClassLoader().getResource("day13.txt").toURI()));

        JSONArray left = null;
        JSONArray right = null;
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i <= lines.size(); i++) {
            if (i % 3 == 0) {
                // Create left
                left = new JSONArray(lines.get(i));
            } else if (i % 3 == 1) {
                // Create right
                right = new JSONArray(lines.get(i));
            } else {
                // Compare
                if(compare(left, right) <= 0) {
                    indexes.add((i/3)+1);
                }
            }
        }

        System.out.println(indexes.stream().reduce(0, Integer::sum));

        List<String> extra = List.of("[[2]]", "[[6]]");

        List<String> linesTask2 =
                Stream.concat(lines.stream().filter(s -> !s.isEmpty() && !s.isBlank()), extra.stream())
                .collect(Collectors.toList());

        List<String> task2Sorted = linesTask2.stream()
                .sorted((a,b) -> compare(new JSONArray(a),new JSONArray(b)))
                .collect(Collectors.toList());

        System.out.println((1 + task2Sorted.indexOf(extra.get(0))) * (1 + task2Sorted.indexOf(extra.get(1))));
    }

    private int compare(Object left, Object right) {
        if(left instanceof Integer leftInt && right instanceof Integer rightInt)
            return leftInt - rightInt;

        if(left instanceof JSONArray leftList && right instanceof JSONArray rightList) {
            if(leftList.isEmpty()) {
                return rightList.isEmpty() ? 0 : -1;
            }

            var size = Math.min(leftList.length(), rightList.length());
            for(int i = 0; i < size; i++) {
                var leftItem = leftList.get(i);
                var rightItem = rightList.get(i);

                var itemCheck = compare(leftItem, rightItem);
                if(itemCheck != 0) {
                    return itemCheck;
                }
            }

            return leftList.length() - rightList.length();
        }

        if(left instanceof Integer) {
            return compare(new JSONArray("["+left+"]"), right);
        }

        if(right instanceof Integer) {
            return compare(left, new JSONArray("["+right+"]"));
        }

        throw new IllegalStateException("Malformed input");
    }

}
