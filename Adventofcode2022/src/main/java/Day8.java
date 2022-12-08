import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day8 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        Day8 run = new Day8();
        run.run();
    }

    public void run() throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Path.of(Day8.class.getClassLoader().getResource("day8.txt").toURI()));
        task1version2(lines);
        task2version2(lines);
    }

    public Integer countTrees(Deque<Integer> integerDeque, Function<Deque<Integer>, Integer> func, int value) {
        int count = 0;

        while (!integerDeque.isEmpty()){
            count++;
            if(func.apply(integerDeque) >= value)
                break;
        }

        return count;
    }

    private void task2version2(List<String> lines) {
        List<List<Integer>> rows = lines.stream().map(s -> Arrays.stream(s.split("")).map(Integer::parseInt).collect(Collectors.toList())).collect(Collectors.toList());
        List<List<Integer>> columns = new ArrayList<>();
        HashMap<String, Integer> visibleTreeCount = new HashMap<>();

        for (int i = 0; i < rows.size(); i++) {
            int finalI = i;
            columns.add(rows.stream().map(p -> p.get(finalI)).collect(Collectors.toList()));
        }

        for (int y = 0; y < rows.size() - 1; y++) {
            for (int x = 0; x < rows.size() - 1; x++) {
                int value = rows.get(y).get(x);
                Deque<Integer> yplus = columns.get(x).subList(y + 1, columns.size()).stream().collect(Collectors.toCollection(ArrayDeque::new));
                Deque<Integer> yminus = columns.get(x).subList(0, y).stream().collect(Collectors.toCollection(ArrayDeque::new));
                Deque<Integer> xplus = rows.get(y).subList(x + 1, rows.size()).stream().collect(Collectors.toCollection(ArrayDeque::new));
                Deque<Integer> xminus = rows.get(y).subList(0, x).stream().collect(Collectors.toCollection(ArrayDeque::new));

                List<Integer> values = List.of(
                        countTrees(yplus, Deque::pop, value),
                        countTrees(yminus, Deque::pollLast, value),
                        countTrees(xplus, Deque::pop, value),
                        countTrees(xminus, Deque::pollLast, value)
                );

                visibleTreeCount.put("y:" + y + "x:" + x, values.stream().filter(i -> i > 0).reduce(1, (a, b) -> a * b));
            }
        }

        System.out.println(visibleTreeCount.values().stream().max(Integer::compareTo).get());
    }

    public void task1version2(List<String> lines) {
        List<List<Integer>> rows = lines.stream().map(s -> Arrays.stream(s.split("")).map(Integer::parseInt).collect(Collectors.toList())).collect(Collectors.toList());
        List<List<Integer>> columns = new ArrayList<>();
        int count = 0;

        for (int i = 0; i < rows.size(); i++) {
            int finalI = i;
            columns.add(rows.stream().map(p -> p.get(finalI)).collect(Collectors.toList()));
        }

        for (int y = 1; y < rows.size() - 1; y++) {
            for (int x = 1; x < rows.size() - 1; x++) {
                int value = rows.get(y).get(x);

                boolean visible =
                        // Y+ direction
                        columns.get(x).subList(y + 1, columns.size()).stream().allMatch(v -> v < value) ||
                                // Y- direction
                                columns.get(x).subList(0, y).stream().allMatch(v -> v < value) ||
                                // X+ direction
                                rows.get(y).subList(x + 1, rows.size()).stream().allMatch(v -> v < value) ||
                                // X- direction
                                rows.get(y).subList(0, x).stream().allMatch(v -> v < value);

                count = visible ? count + 1 : count;
            }
        }

        System.out.println(count + (4 * rows.size()) - 4);
    }
    @Deprecated

    private void task2(List<String> lines) {
        Set<String> visible = new HashSet<>();

        int[][] grid = initMap(lines);
        HashMap<String, Integer> visibleTreeCount = new HashMap<>();

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                List<Integer> multiply = new ArrayList<>();
                int countVisibleTrees = 0;
                // Check top
                for (int dirTop = y - 1; dirTop >= 0; dirTop--) {
                    countVisibleTrees++;
                    if (grid[y][x] <= grid[dirTop][x]) {
                        break;
                    }
                }

                if (countVisibleTrees != 0)
                    multiply.add(countVisibleTrees);
                countVisibleTrees = 0;

                // Check bottom
                for (int dirBottom = y + 1; dirBottom < grid.length; dirBottom++) {
                    countVisibleTrees++;
                    if (grid[y][x] <= grid[dirBottom][x]) {
                        break;
                    }
                }

                if (countVisibleTrees != 0)
                    multiply.add(countVisibleTrees);
                countVisibleTrees = 0;

                // Check left
                for (int dirLeft = x - 1; dirLeft >= 0; dirLeft--) {
                    countVisibleTrees++;
                    if (grid[y][x] <= grid[y][dirLeft]) {
                        break;
                    }
                }

                if (countVisibleTrees != 0)
                    multiply.add(countVisibleTrees);
                countVisibleTrees = 0;

                // Check right
                for (int dirLeft = x + 1; dirLeft < grid[y].length; dirLeft++) {
                    countVisibleTrees++;
                    if (grid[y][x] <= grid[y][dirLeft]) {
                        break;
                    }
                }

                if (countVisibleTrees != 0)
                    multiply.add(countVisibleTrees);
                countVisibleTrees = 0;

                visibleTreeCount.put("y:" + y + "x:" + x, multiply.stream().reduce(1, (a, b) -> a * b));
            }
        }

        System.out.println(visibleTreeCount.values().stream().max(Integer::compareTo).get());
    }

    @Deprecated
    public void task1(List<String> lines) {
        Set<String> visible = new HashSet<>();

        int[][] grid = initMap(lines);

        // Task 1
        for (int y = 1; y < grid.length - 1; y++) {
            for (int x = 1; x < grid[y].length - 1; x++) {
                boolean canBeSeen = false;
                boolean canNotBeSeen = false;
                // Check top
                for (int dirTop = y - 1; dirTop >= 0 && !canBeSeen; dirTop--) {
                    if (grid[y][x] <= grid[dirTop][x]) {
                        canNotBeSeen = true;
                        break;
                    }
                }

                canBeSeen = canNotBeSeen ? false : true;
                canNotBeSeen = false;
                // Check bottom
                for (int dirBottom = y + 1; dirBottom < grid.length && !canBeSeen; dirBottom++) {
                    if (grid[y][x] <= grid[dirBottom][x]) {
                        canNotBeSeen = true;
                        break;
                    }
                }

                canBeSeen = canNotBeSeen ? false : true;
                canNotBeSeen = false;
                // Check left
                for (int dirLeft = x - 1; dirLeft >= 0 && !canBeSeen; dirLeft--) {
                    if (grid[y][x] <= grid[y][dirLeft]) {
                        canNotBeSeen = true;
                        break;
                    }
                }

                canBeSeen = canNotBeSeen ? false : true;
                canNotBeSeen = false;
                // Check right
                for (int dirLeft = x + 1; dirLeft < grid[y].length && !canBeSeen; dirLeft++) {
                    if (grid[y][x] <= grid[y][dirLeft]) {
                        canNotBeSeen = true;
                        break;
                    }
                }
                canBeSeen = canNotBeSeen ? false : true;
                if (canBeSeen)
                    visible.add("y:" + y + "x:" + x);
            }
        }

        System.out.println(visible.size() + ((2 * grid.length) + (2 * grid[0].length) - 4));
    }
    @Deprecated

    public int[][] initMap(List<String> lines) {
        int[][] grid = new int[lines.size()][lines.get(0).length()];
        for (int y = 0; y < lines.size(); y++) {
            List<Integer> trees = Arrays.stream(lines.get(y).split("")).map(Integer::parseInt).collect(Collectors.toList());
            for (int x = 0; x < trees.size(); x++) {
                grid[y][x] = trees.get(x);
            }
        }

        return grid;
    }

}
