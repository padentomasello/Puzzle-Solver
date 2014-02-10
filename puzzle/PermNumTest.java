package puzzle;
import org.junit.Test;
import static org.junit.Assert.*;
/** Tests of Parser class.
 *  @author Daniel Paden Tomasello cs61b-bz*/
public class PermNumTest {
    @Test
    public void testPermNum() throws Exception {
        PermNum x;
        x = new PermNum(3, 9);
        assertEquals("PermNum is working incorrectly", 0, x.get(0));
        x.increment(0);
        x.increment(0);
        x.increment(0);
        assertEquals("PermNum is working incorrectly", 0, x.get(0));
        assertEquals("PermNum is working incorrectly", 1, x.get(1));
    }
}


