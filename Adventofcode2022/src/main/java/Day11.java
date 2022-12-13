import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day11 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        Day11 run = new Day11();
        run.run();
    }

    public void run() throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Path.of(Day11.class.getClassLoader().getResource("day11.txt").toURI()));
        // Task 1
        taskV2(lines);
        // Task 2
    }


    private void taskV2(List<String> lines) {
        List<Monkey> monkeys = new ArrayList<>();

        Monkey monkey = null;

        Pattern monkeyPattern = Pattern.compile("Monkey (\\d):");
        Pattern multiplyPattern = Pattern.compile("Operation: new = old \\* (\\d{1,2})");
        Pattern additionPattern = Pattern.compile("Operation: new = old \\+ (\\d{1,2})");
        Pattern testPattern = Pattern.compile("Test: divisible by (\\d{1,2})");
        Pattern truePattern = Pattern.compile("If true: throw to monkey (\\d)");
        Pattern falsePattern = Pattern.compile("If false: throw to monkey (\\d)");

        for (String line : lines) {
            String trimmed = line.trim();

            Matcher monkeyMatch = monkeyPattern.matcher(trimmed);
            if (monkeyMatch.find()) {
                monkey = new Monkey(monkeyMatch.group(1));
                monkeys.add(monkey);
                continue;
            }

            Matcher multiplyMatch = multiplyPattern.matcher(trimmed);
            if (multiplyMatch.find()) {
                monkey.setMultiplyValue(multiplyMatch.group(1));
                continue;
            }

            Matcher additionMatch = additionPattern.matcher(trimmed);
            if (additionMatch.find()) {
                monkey.setAdditionValue(additionMatch.group(1));
                continue;
            }

            Matcher testMatch = testPattern.matcher(trimmed);
            if (testMatch.find()) {
                monkey.setTestValue(testMatch.group(1));
                continue;
            }

            Matcher trueMatch = truePattern.matcher(trimmed);
            if (trueMatch.find()) {
                monkey.setTrueValue(trueMatch.group(1));
                continue;
            }

            Matcher falseMatch = falsePattern.matcher(trimmed);
            if (falseMatch.find()) {
                monkey.setFalseValue(falseMatch.group(1));
                continue;
            }

            if (trimmed.startsWith("Starting items: ")) {
                String[] items = trimmed.replace("Starting items: ", "").split(", ");
                for (String item : items) {
                    monkey.addItem(item);
                }
                continue;
            }

            if (trimmed.equals("Operation: new = old * old")) {
                monkey.square();
            }
        }

        Map<Integer, Monkey> monkeyMap = monkeys.stream().collect(Collectors.toMap(a -> a.getId(), a -> a));

        for (int i = 0; i < 10000; i++) {
            for (Monkey monkey1 : monkeys) {
                monkey1.makeRound(monkeyMap);
            }
        }
        
        System.out.println(monkeys.stream()
                .map(Monkey::getInspections)
                .sorted(Comparator.reverseOrder())
                .limit(2)
                .map(BigInteger::valueOf)
                .reduce(BigInteger.ONE, (a, b) -> a.multiply(b)));
    }

    public class Monkey {

        private final int id;

        private int multi = -1;
        private int addtion = -1;
        private int test = -1;
        private int trueValue = -1;
        private int falseValue = -1;

        private int inspections = 0;

        public String name;
        public Deque<Long> items = new ArrayDeque<>();

        public int getInspections() {
            return inspections;
        }

        public Integer getId() {
            return id;
        }

        public Monkey(String group) {
            this.id = Integer.parseInt(group);
        }

        public void setMultiplyValue(String group) {
            this.multi = Integer.parseInt(group);
        }

        public void setAdditionValue(String group) {
            this.addtion = Integer.parseInt(group);
        }

        public void setTestValue(String group) {
            this.test = Integer.parseInt(group);
        }

        public void setTrueValue(String group) {
            this.trueValue = Integer.parseInt(group);
        }

        public void setFalseValue(String group) {
            this.falseValue = Integer.parseInt(group);
        }

        public void addItem(String item) {
            addItem(Long.parseLong(item));
        }

        public void addItem(long item) {
            items.add(item);
        }

        public void makeRound(Map<Integer, Monkey> monkeyMap) {
            while (!items.isEmpty()) {
                long item = this.items.pop();

                if (multi == -2) {
                    item *= item;
                } else if (multi != -1) {
                    item *= multi;
                } else if (addtion != -1) {
                    item += addtion;
                }

                inspections++;

//                item = Math.floorDiv(item, 3);
                item %= 9699690;

                if (item % test == 0) {
                    monkeyMap.get(trueValue).addItem(item);
                } else {
                    monkeyMap.get(falseValue).addItem(item);
                }
            }
        }

        public void square() {
            this.multi = -2;
        }
    }
}
