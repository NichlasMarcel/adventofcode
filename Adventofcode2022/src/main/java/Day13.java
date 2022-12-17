import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day13 {

    private String testInput =
            "Sabqponm\n" +
                    "abcryxxl\n" +
                    "accszExk\n" +
                    "acctuvwj\n" +
                    "abdefghi";

    public static void main(String[] args) throws URISyntaxException, IOException {
        Day13 run = new Day13();
        run.run();
    }

    public void run() throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Path.of(Day13.class.getClassLoader().getResource("day13.txt").toURI()));

        Node left = null;
        Node right = null;
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i <= lines.size(); i++) {
            if (i % 3 == 0) {
                // Create left
                left = getComparisonGroup(lines, i);
            } else if (i % 3 == 1) {
                // Create right
                right = getComparisonGroup(lines, i);
            } else {
                // Compare
//                System.out.println((i/3)+1 + ":" + compare(left.items, right.items));
                if(compare(left.items, right.items)) {
                    indexes.add((i/3)+1);
                }
            }
        }

        System.out.println(indexes.stream().reduce(0, Integer::sum));

    }

    public Boolean compare(Deque<Object> left, Deque<Object> right) {

        while (!left.isEmpty() && !right.isEmpty()) {
            Object leftElement = left.pop();
            Object rightElement = right.pop();

            if(leftElement instanceof Integer leftInt && rightElement instanceof Integer rightInt) {
                if(leftInt < rightInt)
                    return true;
                else if(leftInt > rightInt)
                    return false;
            } else if(leftElement instanceof Node leftNode && rightElement instanceof Node rightNode) {
                Deque<Object> leftList = leftNode.getItems();
                Deque<Object> rightList = rightNode.getItems();

                if(!leftList.isEmpty() && rightList.isEmpty())
                    return false;
                else if (leftList.isEmpty() && !rightList.isEmpty())
                    return true;
                else if (!leftList.isEmpty() && !rightList.isEmpty()) {
                    Boolean result = compare(leftList, rightList);
                    if(result != null)
                        return result;
                }
            } else if(leftElement instanceof Integer && rightElement instanceof Node rightNode) {
                Deque<Object> leftList = List.of(leftElement).stream().collect(Collectors.toCollection(ArrayDeque::new));
                Deque<Object> rightList = rightNode.getItems();
                return compare(leftList, rightList);
            } else if(rightElement instanceof Integer && leftElement instanceof Node leftNode) {
                Deque<Object> leftList = leftNode.getItems();
                Deque<Object> rightList = List.of(rightElement).stream().collect(Collectors.toCollection(ArrayDeque::new));
                return compare(leftList, rightList);
            }
        }

        if(left.isEmpty() && !right.isEmpty())
            return true;
        else if(!left.isEmpty() && right.isEmpty())
            return false;
        else
            return null;
    }

    private Node getComparisonGroup(List<String> lines, int i) {
        Node start = null;
        Node currentGroup = null;

        String[] chars = lines.get(i).split("");
        for (int j = 0; j < chars.length; j++) {
            if (chars[j].equals("[")) {
                if (currentGroup == null) {
                    start = new Node();
                    currentGroup = start;
                } else {
                    Node node = new Node();
                    node.setPrev(currentGroup);
                    currentGroup.getItems().addLast(node);
                    currentGroup = node;
                }
            } else if (chars[j].equals("]")) {
                currentGroup = currentGroup.getPrev();
            } else if (chars[j].matches("\\d+")) {
                currentGroup.getItems().addLast(Integer.parseInt(chars[j]));
            }
        }

        return start;
    }

    public class Node {
        private Node prev = null;
        private Deque<Object> items = new ArrayDeque<>();

        public Node() {

        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public Deque<Object> getItems() {
            return items;
        }

        public void setItems(Deque<Object> items) {
            this.items = items;
        }
    }

}
