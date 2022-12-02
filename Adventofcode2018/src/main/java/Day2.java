import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public class Day2 {



    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Path.of(Day1.class.getClassLoader().getResource("day2.txt").toURI()));

        // Part 1
        int two = 0;
        int three = 0;

        for(String line : lines) {
            HashMap<String, Integer> countTrack = new HashMap<>();
            for(int x = 0; x < line.length(); x++) {
                String c = line.substring(x, x + 1);
                Integer value = countTrack.containsKey(c) ? countTrack.get(c) + 1 : 1;
                countTrack.put(c, value);
            }

            if(countTrack.values().stream().anyMatch(p -> p.equals(2))) {
                two++;
            }

            if(countTrack.values().stream().anyMatch(p -> p.equals(3))) {
                three++;
            }
        }

        System.out.println(two * three);

        // Part 2
        int diffCount = 0;
        for(int x = 0; x < lines.size() - 1; x++) {
            for(int y = x + 1; y < lines.size(); y++) {
                int diff = diffInChar(lines.get(x), lines.get(y));
                if(diff == 1) {
                    System.out.println(lines.get(x));
                    System.out.println(lines.get(y));
                    diffCount++;
                }
            }
        }

        System.out.println(diffCount);

    }

    public static int diffInChar(String s1, String s2) {
        int diff = 0;
        char[] c1arr = s1.toCharArray();
        char[] c2arr = s2.toCharArray();

        for(int i = 0; i < c1arr.length; i++) {
            if(c1arr[i] != c2arr[i]) {
                diff++;
            }
        }

        return diff;
    }
}
