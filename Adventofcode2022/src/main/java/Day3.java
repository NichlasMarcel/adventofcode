import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day3 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Path.of(Day3.class.getClassLoader().getResource("day3.txt").toURI()));

        // Task 1
        System.out.println(lines.stream()
                .map(Day3::getIntersectionCharacter)
                .flatMap(p -> p.isEmpty() ? Stream.empty() : Stream.of(p.get()))
                .map(Day3::getPriority)
                .reduce(0, Integer::sum));

        // Task 2
        System.out.println(
                batches(lines, 3)
                        .map(p -> p.toArray(new String[0]))
                        .map(Day3::intersection)
                        .flatMap(p -> p.isEmpty() ? Stream.empty() : Stream.of(p.get()))
                        .map(Day3::getPriority)
                        .reduce(0, Integer::sum));
    }

    // Part 1
    private static Optional<Character> getIntersectionCharacter(String line) {
        String firstCompartment = line.substring(0, (line.length() / 2));
        String secondCompartment = line.substring(line.length() / 2);
        return intersection(firstCompartment, secondCompartment);
    }

    public static int getPriority(char c) {
        return Character.isUpperCase(c) ? Character.getNumericValue(c) - 9 + 26 : Character.getNumericValue(c) - 9;
    }

    public static Optional<Character> intersection(String... a) {
        Stream<Character> charSet = a[0].chars()
                .mapToObj(c -> (char) c)
                .distinct();
        for (int i = 1; i < a.length; i++) {
            charSet = Day3.filter(charSet, a[i]);
        }

        return charSet.findFirst();
    }

    public static Stream<Character> filter(Stream<Character> stream, String compareTo) {
        return stream.filter(x -> compareTo.chars().mapToObj(y -> (char) y).anyMatch(y -> y.equals(x)));
    }


    // Part 2
    public static <T> Stream<List<T>> batches(List<T> source, int length) {
        if (length <= 0)
            throw new IllegalArgumentException("length = " + length);
        int size = source.size();
        if (size <= 0)
            return Stream.empty();
        int fullChunks = (size - 1) / length;
        return IntStream.range(0, fullChunks + 1).mapToObj(
                n -> source.subList(n * length, n == fullChunks ? size : (n + 1) * length));
    }
}
