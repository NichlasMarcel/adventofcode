import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Day1 {

    private static Map<String, Integer> textToNum = new HashMap<>() {{
        put("one", 1);
        put("two", 2);
        put("three", 3);
        put("four", 4);
        put("five", 5);
        put("six", 6);
        put("seven", 7);
        put("eight", 8);
        put("nine", 9);
    }};

    public static void main(String[] args) throws URISyntaxException, IOException {

        List<String> lines = Files.readAllLines(Path.of(Day1.class.getClassLoader().getResource("day1.txt").toURI()));

        int sum = 0;

        for(String line: lines) {
            int firstDigit = getForwardSearchDigit(line);
            int secondDigit = getForwardSearchDigit(new StringBuilder(line).reverse().toString());

            sum += (firstDigit * 10) + secondDigit;
        }

        System.out.println(sum);

    }

    public static int getForwardSearchDigit(String line) {
        for(int i = 0; i < line.length(); i++) {
            if(Character.isDigit(line.charAt(i))) {
                return Integer.parseInt(line.charAt(i) + "");
            }

            if(textToNum.containsKey(line.substring(i, Math.min(i + 3, line.length() - 1))))
                return textToNum.get(line.substring(i, Math.min(i + 3, line.length() - 1)));

            if(textToNum.containsKey(line.substring(i, Math.min(i + 4, line.length() - 1))))
                return textToNum.get(line.substring(i, Math.min(i + 4, line.length() - 1)));

            if(textToNum.containsKey(line.substring(i, Math.min(i + 5, line.length() - 1))))
                return textToNum.get(line.substring(i, Math.min(i + 5, line.length() - 1)));

        }

        return 0;
    }

}
