import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Day10 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        Day10 run = new Day10();
        run.run();
    }

    public void run() throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Path.of(Day10.class.getClassLoader().getResource("day10.txt").toURI()));
        // Task 1
        task(lines);
    }

    private void task(List<String> lines) {
        int cycles = 0;
        int registerX = 1;

        HashMap<Integer, Integer> cyclesAndRegister = new HashMap<>();
        for(int i = 0; i < lines.size(); i++) {
            String instruction = lines.get(i);
            if("noop".equals(instruction)) {
                cyclesAndRegister.put(++cycles, registerX);
            } else {
                Integer val = Integer.parseInt(instruction.split(" ")[1]);
                cyclesAndRegister.put(++cycles, registerX);
                cyclesAndRegister.put(++cycles, registerX);
                registerX+=val;
            }
        }
        int sum = 0;
        for(int i = 20; i <= 220; i+=40) {
            sum += (i * cyclesAndRegister.get(i));
        }
        System.out.println(sum);

        String print = "";
        for(int i = 1; i <= 240; i++) {
            print += Math.abs(cyclesAndRegister.get(i) - ((i - 1) % 40)) <= 1 ? "#" : ".";
        }

        for(int i = 40; i <= 240; i+=40) {
            System.out.println(print.substring(i - 40, i));
        }
    }
}
