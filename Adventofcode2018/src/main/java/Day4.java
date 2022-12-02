import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Path.of(Day4.class.getClassLoader().getResource("day4.txt").toURI()));

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Pattern localDateTimePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}");
        Pattern eventPattern = Pattern.compile("(?!(\\[\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}\\]))([A-Za-z]+).+");

        List<Guard> events = new ArrayList<>();

        for (String line : lines) {
            Matcher localDateTimeMatcher = localDateTimePattern.matcher(line);
            Matcher eventMatcher = eventPattern.matcher(line);

            localDateTimeMatcher.find();
            eventMatcher.find();

            events.add(new Guard(eventMatcher.group(), LocalDateTime.parse(localDateTimeMatcher.group(), dateTimeFormatter)));

        }

    }

    public static class Guard {
        private String event;
        private LocalDateTime timestamp;

        public Guard(String event, LocalDateTime timestamp){

        }

        public Guard(String event, String timestamp){

        }
    }
}
