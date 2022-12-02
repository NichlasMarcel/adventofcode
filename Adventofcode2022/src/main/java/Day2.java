import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day2 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Path.of(Day2.class.getClassLoader().getResource("day2.txt").toURI()));
        // Task 1
        Map<String, String> bestpick = Map.of("X", "A", "Y", "B", "Z", "C");
        System.out.println(lines.stream()
                .map(line -> line.split(" "))
                .map(game -> new String[]{game[0], bestpick.get(game[1])})
                .map(Day2::getPoint)
                .reduce(0, Integer::sum));

        // Task 2
        List<String> options = List.of("A", "B", "C");
        Map<String, Integer> findIndexForStrategy = Map.of("X", 2, "Y", 0, "Z", 1);
        System.out.println(lines.stream()
                .map(line -> line.split(" "))
                .map(game -> new String[]{game[0], options.get((options.indexOf(game[0]) + findIndexForStrategy.get(game[1])) % 3)})
                .map(Day2::getPoint)
                .reduce(0, Integer::sum));
            }

    public static int getPoint(String... play) {
        String opponentHand = play[0];
        String ourHand = play[1];

        int shape = ourHand.equals("A") ? 1 : ourHand.equals("B") ? 2 : 3;

        if (opponentHand.equals(ourHand))
            return 3 + shape;
        if (opponentHand.equals("A")) {
            if (ourHand.equals("C")) {
                return 0 + shape;
            } else {
                return 6 + shape;
            }
        } else if (opponentHand.equals("B")) {
            if (ourHand.equals("A")) {
                return 0 + shape;
            } else {
                return 6 + shape;
            }
        } else {
            if (ourHand.equals("B")) {
                return 0 + shape;
            } else {
                return 6 + shape;
            }
        }
    }

}
