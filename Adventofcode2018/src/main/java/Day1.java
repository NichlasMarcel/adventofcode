import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day1 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<Integer> frequencyChanges = Files.readAllLines(Path.of(Day1.class.getClassLoader().getResource("day1.txt").toURI())).stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        // Part 1
        System.out.println(frequencyChanges.stream().reduce(0, Integer::sum));
        
        // Part 2
        int result = 0;
        int count = 0;

        HashMap<Integer, Integer> map = new HashMap<>();
        while (true) {
            if (map.containsKey(result)) {
                System.out.println(result);
                break;
            }
            map.put(result, 1);
            result += frequencyChanges.get(count % frequencyChanges.size());
            count++;
        }
    }
}
