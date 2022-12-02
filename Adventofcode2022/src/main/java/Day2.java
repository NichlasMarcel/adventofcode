import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day2 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Path.of(Day2.class.getClassLoader().getResource("day2.txt").toURI()));
        // A = Rock, B = Paper, C = Scissor

        // Task 1
        Map<String, String> bestpick = Map.of("X", "A", "Y", "B", "Z", "C");
        int point = 0;
        for (String game : lines) {
            String opponentHand = game.trim().replace(" ","").substring(0, 1);
            String ourHand = game.trim().replace(" ","").substring(1, 2);
            point += getPoint(opponentHand, bestpick.get(ourHand));
        }

        System.out.println(point);


        // Task 2
        // x = lose, y = draw, z = win: Total points?
        List<String> options = List.of("A", "B", "C");
        Map<String, Integer> findIndexForStrategy = Map.of("X", 2, "Y", 0, "Z", 1);
        int pointT2 = 0;
        for(String line : lines) {
            String opponentHand = line.trim().replace(" ","").substring(0, 1);
            String strategy = line.trim().replace(" ","").substring(1, 2);

            pointT2 += getPoint(opponentHand, options.get((options.indexOf(opponentHand) + findIndexForStrategy.get(strategy)) % 3));
        }
        System.out.println(pointT2);

    }

    public static int getPoint(String opponentHand, String ourHand) {

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
