import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class Day9 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        Day9 run = new Day9();
        run.run();
    }

    public void run() throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Path.of(Day9.class.getClassLoader().getResource("day9.txt").toURI()));
        // Task 1
        task(lines, 2);
        // Task 2
        task(lines, 10);
    }

    private void task(List<String> lines, int links) {

        int[][] snake = new int[links][2];

        for (int i = 0; i < links; i++) {
            snake[i] = new int[]{0, 0};
        }

        HashSet<String> positions = new HashSet<>();
        positions.add(snake[links - 1][0] + ":" + snake[links - 1][1]);

        for (String move : lines) {
            String[] moves = move.split(" ");
            String dir = moves[0];

            int steps = Integer.parseInt(moves[1]);

            for (int i = 0; i < steps; i++) {
                if (dir.equals("R")) {
                    snake[0][0]++;
                } else if (dir.equals("L")) {
                    snake[0][0]--;
                } else if (dir.equals("U")) {
                    snake[0][1]--;
                } else if (dir.equals("D")) {
                    snake[0][1]++;
                }

                for (int x = 1; x < links; x++) {
                    if (!inContact(snake[x - 1], snake[x])) {
                        if(snake[x - 1][0] - snake[x][0] == 0) {
                            snake[x][1] = snake[x - 1][1] - snake[x][1] > 0 ? snake[x][1] + 1 : snake[x][1] - 1;
                        } else if(snake[x - 1][1] - snake[x][1] == 0) {
                            snake[x][0] = snake[x - 1][0] - snake[x][0] > 0 ? snake[x][0] + 1 : snake[x][0] - 1;
                        } else {
                            snake[x][1] = snake[x - 1][1] - snake[x][1] > 0 ? snake[x][1] + 1 : snake[x][1] - 1;
                            snake[x][0] = snake[x - 1][0] - snake[x][0] > 0 ? snake[x][0] + 1 : snake[x][0] - 1;
                        }
                    }
                }
                positions.add(snake[links - 1][0] + ":" + snake[links - 1][1]);
            }
        }

        System.out.println(positions.size());
    }

    public boolean inContact(int[] headPos, int[] tailPos) {

        for (int x = tailPos[0] - 1; x <= (tailPos[0] + 1); x++) {
            for (int y = tailPos[1] - 1; y <= (tailPos[1] + 1); y++) {
                if (x == headPos[0] && y == headPos[1]) {
                    return true;
                }
            }
        }

        return false;
    }
}
