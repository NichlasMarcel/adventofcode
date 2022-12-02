import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day1 {

    public static void main(String[] args) throws URISyntaxException, IOException {

        List<String> lines = Files.readAllLines(Path.of(Day1.class.getClassLoader().getResource("day1.txt").toURI()));
        HashMap<String, Integer> elfCalories = new HashMap<>();
        int index = 1;

        for (String line : lines) {
            if(line == null || line.isEmpty()) {
                index++;
            } else {
                String elfName = "elf"+index;
                int calories = Integer.parseInt(line);

                if(elfCalories.containsKey(elfName)) {
                    elfCalories.put(elfName, elfCalories.get(elfName) + calories);
                } else {
                    elfCalories.put(elfName, calories);
                }
            }
        }

        // Task 1
        System.out.println(elfCalories.values().stream().max(Integer::compare).get());

        // Task2
        List<Integer> elfCaloriesList = elfCalories.values().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        System.out.println(elfCaloriesList.get(0) + elfCaloriesList.get(1) + elfCaloriesList.get(2));


    }
}
