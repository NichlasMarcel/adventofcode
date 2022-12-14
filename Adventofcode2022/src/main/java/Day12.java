import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day12 {

    private String testInput =
                    "Sabqponm\n" +
                    "abcryxxl\n" +
                    "accszExk\n" +
                    "acctuvwj\n" +
                    "abdefghi";

    public static void main(String[] args) throws URISyntaxException, IOException {
        Day12 run = new Day12();
        run.run();
    }

    public void run() throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Path.of(Day12.class.getClassLoader().getResource("day12.txt").toURI()));
        task(lines);
    }


    private void task(List<String> lines) {

        Node[][] matrix = new Node[lines.size()][lines.get(0).length()];
        Node start = null;
        Node end = null;

        // First create nodes
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                char readChar = lines.get(i).split("")[j].charAt(0);
                Node node = new Node(j + ":" + i, Character.toLowerCase(readChar), j, i);
                matrix[i][j] = node;
                if (readChar == 'S') {
                    matrix[i][j].height = 'a';
                    start = node;
                } else if (readChar == 'E') {
                    end = node;
                    matrix[i][j].height = 'z';
                }
            }
        }

        // Create adjacentNodes
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                for (int y = (i - 1) >= 0 ? i - 1 : 0; matrix.length > y && y <= i + 1; y++) {
                    for (int x = (j - 1) >= 0 ? j - 1 : 0; matrix[y].length > x && x <= j + 1; x++) {
                        if (y == i && x == j)
                            continue;

                        if ((y == i && x != j) || (y != i && x == j)) {
                            if (Math.abs(matrix[i][j].height - matrix[y][x].height) == 1 || matrix[i][j].height - matrix[y][x].height > -1) {
                                matrix[i][j].addDestination(matrix[y][x], 1);
                            }
                        }
                    }
                }
            }
        }

        Set<Node> nodes = Arrays.stream(matrix).flatMap(p -> Arrays.stream(p)).collect(Collectors.toSet());

        List<Node> path = dijkstra(start, end, nodes);
        System.out.println(path.size());

        int dist = Integer.MAX_VALUE;

        List<Node> startingNodes = nodes.stream().filter(p -> p.height == 'a').collect(Collectors.toList());
        for(Node startingNode : startingNodes) {
            int length = dijkstra(startingNode, end, nodes).size();
            dist = dist > length && length != 0 ? length : dist;
        }

        System.out.println(dist);
    }

    public List<Node> dijkstra(Node start, Node end, Set<Node> graph) {
        graph.stream().forEach(p -> {
            p.setDistance(Double.MAX_VALUE);
            p.prev = null;
        });

        start.setDistance(0.0);

        List<Node> queue = new ArrayList<>(graph);

        Collections.sort(queue, (a, b) -> (int)(b.distance - a.distance));

        while (!queue.isEmpty()) {
            Node currentNode = queue.remove(queue.size() - 1);
            for (Node adjacentNode : currentNode.getAdjacentNodes().keySet()) {
                double dist = currentNode.distance + currentNode.getAdjacentNodes().get(adjacentNode);
                if(dist < adjacentNode.distance) {
                    adjacentNode.setDistance(dist);
                    adjacentNode.setPrev(currentNode);
                }
            }
            Collections.sort(queue, (a, b) -> (int)(b.distance - a.distance));
        }

        List<Node> path = new ArrayList<>();
        Node nodeOnPath = end;
        while(nodeOnPath.prev != null) {
            path.add(nodeOnPath);
            nodeOnPath = nodeOnPath.prev;
        }

        return path;
    }

    public class Node {

        private String name;
        int x, y;

        private Double distance = Double.MAX_VALUE;

        char height;

        Node prev;


        Map<Node, Double> adjacentNodes = new HashMap<>();

        public void addDestination(Node destination, double distance) {
            adjacentNodes.put(destination, distance);
        }

        public Node(String name, char height, int x, int y) {
            this.name = name;
            this.height = height;
            this.x = x;
            this.y = y;
        }

        public String getName() {
            return name;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }

        public Map<Node, Double> getAdjacentNodes() {
            return adjacentNodes;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

    }
}
