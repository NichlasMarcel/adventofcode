import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day5 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        Day5 day5 = new Day5();
        day5.run();
    }

    public void run() throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Path.of(Day5.class.getClassLoader().getResource("day5.txt").toURI()));

        // Init map
        HashMap<Integer, Deque<Character>> stacks = initMap(lines);

        // Task 1
        List<String> moves = lines.subList(10, lines.size());

        for (int i = 0; i < moves.size(); i++) {
            String[] keepNumers = moves.get(i).replaceAll("[^-?0-9]+", " ").trim().split(" ");
            int move = Integer.parseInt(keepNumers[0]);
            int from = Integer.parseInt(keepNumers[1]);
            int to = Integer.parseInt(keepNumers[2]);

            for (int j = 1; j <= move; j++) {
                stacks.get(to).push(stacks.get(from).pop());
            }
        }


        System.out.println(
                Arrays.toString(stacks.values().stream()
                        .map(Deque::peek)
                        .toArray(Character[]::new))
        );

        // Task 2
        stacks = initMap(lines);
        moves = lines.subList(10, lines.size());

        for (int i = 0; i < moves.size(); i++) {
            String[] keepNumers = moves.get(i).replaceAll("[^-?0-9]+", " ").trim().split(" ");
            int move = Integer.parseInt(keepNumers[0]);
            int from = Integer.parseInt(keepNumers[1]);
            int to = Integer.parseInt(keepNumers[2]);

            List<Character> reArrange = new ArrayList<>();
            for (int j = 1; j <= move; j++) {
                reArrange.add(stacks.get(from).pop());
            }

            for(int j = reArrange.size() -1; j >= 0; j--) {
                stacks.get(to).push(reArrange.get(j));
            }
        }


        System.out.println(
                Arrays.toString(stacks.values().stream()
                        .map(Deque::peek)
                        .toArray(Character[]::new))
        );
    }

    private static HashMap<Integer, Deque<Character>> initMap(List<String> lines) {
        HashMap<Integer, Deque<Character>> stacks = new HashMap<>();

        for (int i = 1; i <= 9; i++) {
            stacks.put(i, new ArrayDeque<>());
        }

        List<String> map = lines.subList(0, 8);
        for (int c = map.size() - 1; c >= 0; c--) {
            String s = map.get(c);
            char[] carr = s.toCharArray();
            for (int i = 1; i <= 9; i++) {
                int index = i == 1 ? 1 : ((i - 1) * 4) + 1;
                if (index < carr.length)
                    if (Character.isAlphabetic(carr[index]))
                        stacks.get(i).push(carr[index]);
            }
        }

        return stacks;
    }
}
