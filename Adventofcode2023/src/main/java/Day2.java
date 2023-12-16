import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day2 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Path.of(Day2.class.getClassLoader().getResource("day2.txt").toURI()));
        part2(lines);
    }


    public static void part1(String[] lines) {
        int sumOfIds = 0;

        for(String line: lines) {

            boolean impossible = false;

            for(String set : line.split(";")) {
                Matcher mGreen = Pattern.compile("(\\d+)\\sgreen").matcher(set);
                Matcher mBlue = Pattern.compile("(\\d+)\\sblue").matcher(set);
                Matcher mRed = Pattern.compile("(\\d+)\\sred").matcher(set);

                int sumGreen = findSum(mGreen);
                int sumBlue = findSum(mBlue);
                int sumRed = findSum(mRed);

                if(sumGreen > 13 || sumRed > 12 || sumBlue > 14) {
                    impossible = true;
                    break;
                }
            }

            if(!impossible) {
                Matcher gameId = Pattern.compile("Game\\s(\\d+)").matcher(line);
                gameId.find();
                sumOfIds += Integer.parseInt(gameId.group(1));
            }
        }

        System.out.println(sumOfIds);
    }

    public static int findSum(Matcher m) {
        int sum = 0;
        while(m.find()) {
            sum += Integer.parseInt(m.group(1));
        }

        return sum;
    }

    public static void part2(List<String> lines) {
        int sumOfPowers = 0;

        for(String line: lines) {
            int maxGreen = 0;
            int maxBlue = 0;
            int maxRed = 0;

            for(String set : line.split(";")) {
                Matcher mGreen = Pattern.compile("(\\d+)\\sgreen").matcher(set);
                Matcher mBlue = Pattern.compile("(\\d+)\\sblue").matcher(set);
                Matcher mRed = Pattern.compile("(\\d+)\\sred").matcher(set);

                int sumGreen = findSum(mGreen);
                int sumBlue = findSum(mBlue);
                int sumRed = findSum(mRed);


                maxGreen = Math.max(sumGreen, maxGreen);
                maxBlue = Math.max(sumBlue, maxBlue);
                maxRed = Math.max(sumRed, maxRed);
            }

            sumOfPowers += maxGreen * maxBlue * maxRed;
        }

        System.out.println(sumOfPowers);
    }

}
