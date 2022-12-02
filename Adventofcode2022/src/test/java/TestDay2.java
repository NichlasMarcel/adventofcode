import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestDay2 {

    @Test
    public void testPoint() {
        // A = Rock 1 point
        // B = Paper 2 point
        // C = Scissor 3 point

        // Paper test
        Assertions.assertEquals(6 + 2, Day2.getPoint("A", "B"));
        Assertions.assertEquals(3 + 2, Day2.getPoint("B", "B"));
        Assertions.assertEquals(0 + 2, Day2.getPoint("C", "B"));

        // Rock test
        Assertions.assertEquals(3 + 1, Day2.getPoint("A", "A"));
        Assertions.assertEquals(0 + 1, Day2.getPoint("B", "A"));
        Assertions.assertEquals(6 + 1, Day2.getPoint("C", "A"));

        // Scissor test
        Assertions.assertEquals(0 + 3, Day2.getPoint("A", "C"));
        Assertions.assertEquals(6 + 3, Day2.getPoint("B", "C"));
        Assertions.assertEquals(3 + 3, Day2.getPoint("C", "C"));
    }

    @Test
    public void testCountPoint() {
        List<String> testinput = new ArrayList<>();

        testinput.add("C Z");
        testinput.add("C Y");
        testinput.add("B X");
    }

}
