import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestDay3 {

    @Test
    public void testPriority() {
        // Paper test
        Assertions.assertEquals(1, Day3.getPriority('a'));
        Assertions.assertEquals(2, Day3.getPriority('b'));
        Assertions.assertEquals(3, Day3.getPriority('c'));
        Assertions.assertEquals(26, Day3.getPriority('z'));
        Assertions.assertEquals(27, Day3.getPriority('A'));
        Assertions.assertEquals(28, Day3.getPriority('B'));
        Assertions.assertEquals(29, Day3.getPriority('C'));
        Assertions.assertEquals(52, Day3.getPriority('Z'));
    }


    @Test
    public void testIntersection() {
        // Paper test
        Assertions.assertEquals('a', Day3.intersection("abcde", "aBCDE").get());
        Assertions.assertEquals('B', Day3.intersection("ABcde", "aBCDE").get());
        Assertions.assertEquals('C', Day3.intersection("AbCde", "aBCDE").get());
    }


}
