import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class Day14 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        Day14 run = new Day14();
        run.run();
    }

    public void run() throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Path.of(Day14.class.getClassLoader().getResource("day14.txt").toURI()));

        int count = task1(lines);
        System.out.println(count);

        char[][] grid = createGridTask2(lines);
        System.out.println(task2(grid));
    }

    private static int task2(char[][] grid) {
        int count = 0;
        while(grid[0][500] != '#') {
            boolean reachedBottom = false;
            int[] curYX = new int[] {0, 500};
            while(!reachedBottom) {
                if(grid[curYX[0] + 1][curYX[1]] != '#') {
                    curYX[0]++;
                } else if (grid[curYX[0] + 1][curYX[1] - 1] != '#') {
                    curYX[0]++;
                    curYX[1]--;
                } else if (grid[curYX[0] + 1][curYX[1] + 1] != '#') {
                    curYX[0]++;
                    curYX[1]++;
                } else {
                    reachedBottom = true;
                    grid[curYX[0]][curYX[1]] = '#';
                    count++;
                }
            }
        }
        return count;
    }

    private int task1(List<String> lines) {
        char[][] grid = createGrid(lines);

        boolean notInAbyss = true;
        int count = 0;
        int highestY = 0;
        while(notInAbyss) {
            boolean reachedBottom = false;
            int[] curYX = new int[] {0, 500};
            while(!reachedBottom) {
                if(curYX[0] == 599 || curYX[1] == 599) {
                    notInAbyss = false;
                    break;
                } else if(grid[curYX[0] + 1][curYX[1]] != '#') {
                    curYX[0]++;
                } else if (grid[curYX[0] + 1][curYX[1] - 1] != '#') {
                    curYX[0]++;
                    curYX[1]--;
                } else if (grid[curYX[0] + 1][curYX[1] + 1] != '#') {
                    curYX[0]++;
                    curYX[1]++;
                } else {
                    highestY = curYX[0] > highestY ? curYX[0] : highestY;
                    reachedBottom = true;
                    grid[curYX[0]][curYX[1]] = '#';
                    count++;
                }
            }
        }
        return count;
    }

    public char[][] createGrid(List<String> lines) {
        char[][] grid = new char[600][600];
        for(String line : lines) {
            String[] direction = line.split(" -> ");
            for(int i = 0; i < direction.length - 1; i++) {
                String[] fromxy = direction[i].split(",");
                String[] toxy = direction[i + 1].split(",");

                int fromY = Integer.parseInt(fromxy[1]);
                int fromX = Integer.parseInt(fromxy[0]);
                int toY = Integer.parseInt(toxy[1]);
                int toX = Integer.parseInt(toxy[0]);

                int lineLength = Math.max(Math.abs(fromY - toY), Math.abs(fromX - toX));

                int incrementY = Integer.compare(toY, fromY);
                int incrementX = Integer.compare(toX, fromX);

                for(int pos = 0; pos <= lineLength; pos++) {
                    int indexY = (pos * incrementY) + fromY;
                    int indexX = (pos * incrementX) + fromX;
                    grid[indexY][indexX] = '#';
                }
            }
        }

        return grid;
    }

    public char[][] createGridTask2(List<String> lines) {
        char[][] grid = new char[1000][1000];
        int highestY = 0;
        for(String line : lines) {
            String[] direction = line.split(" -> ");
            for(int i = 0; i < direction.length - 1; i++) {
                String[] fromxy = direction[i].split(",");
                String[] toxy = direction[i + 1].split(",");

                int fromY = Integer.parseInt(fromxy[1]);
                int fromX = Integer.parseInt(fromxy[0]);
                int toY = Integer.parseInt(toxy[1]);
                int toX = Integer.parseInt(toxy[0]);

                int lineLength = Math.max(Math.abs(fromY - toY), Math.abs(fromX - toX));

                int incrementY = Integer.compare(toY, fromY);
                int incrementX = Integer.compare(toX, fromX);

                for(int pos = 0; pos <= lineLength; pos++) {
                    int indexY = (pos * incrementY) + fromY;
                    int indexX = (pos * incrementX) + fromX;
                    highestY = indexY > highestY ? indexY : highestY;
                    grid[indexY][indexX] = '#';
                }
            }
        }
        highestY+=2;
        for(int i = 0; i < grid[highestY].length; i++) {
            grid[highestY][i] = '#';
        }

        return grid;
    }

}
