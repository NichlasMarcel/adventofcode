import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day3 {



    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Path.of(Day1.class.getClassLoader().getResource("day3.txt").toURI()));
        int[][] matrix = new int[1000][1000];

        // Part 1
        for(String line : lines) {
            String[] split = line.split(" ");
            String[] xy = split[2].replace(":", "").split(",");
            String[] wh = split[3].split("x");

            Integer xS = Integer.parseInt(xy[0]);
            Integer yS = Integer.parseInt(xy[1]);
            Integer w = Integer.parseInt(wh[0]);
            Integer h = Integer.parseInt(wh[1]);

            for(int x = xS; x < w + xS; x++) {
                for(int y = yS; y < h + yS; y++) {
                    matrix[y][x] = matrix[y][x] + 1;
                }
            }
        }

        int count = 0;
        for(int x = 0; x < matrix.length; x++) {
            for(int y = 0; y < matrix[x].length; y++) {
                if(matrix[y][x] > 1) {
                    count++;
                }
            }
        }

        System.out.println(count);


        // Part 2
        for(String line : lines) {
            String[] split = line.split(" ");
            String[] xy = split[2].replace(":", "").split(",");
            String[] wh = split[3].split("x");
            Integer xS = Integer.parseInt(xy[0]);
            Integer yS = Integer.parseInt(xy[1]);
            Integer w = Integer.parseInt(wh[0]);
            Integer h = Integer.parseInt(wh[1]);

            boolean stop = false;

            for(int x = xS; x < w + xS && !stop; x++) {
                for(int y = yS; y < h + yS && !stop; y++) {
                    stop =  matrix[y][x] != 1;
                }
            }

            if(!stop) {
                System.out.println(split[0]);
            }
        }

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
