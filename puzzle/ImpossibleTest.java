package puzzle;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

public class ImpossibleTest {
    private StringReader reader;

    /** Set reader to contain the contents of LINES. */
    private void setUp(String... lines) {
        StringBuilder str = new StringBuilder();
        for (String line : lines) {
            str.append(line);
            str.append("\n");
        }
        reader = new StringReader(str.toString());
    }

    /** Test help message ('java solve' with no arguments).
     * @throws Exception */
    @Test
    public void testEcho() throws Exception {
        setUp("Jack lives in the white house.  Jack is the carpenter.",
                "Jill is the clerk.   "
               + "The carpenter lives in the yellow house.",
                "What do you know about Jack?");
        Parser p = Parser.parse(reader);
        Solver solver = new Solver();
        p.inform(solver);
        assertEquals("Impossible method is not working correctly",
                    true, solver.impossible());
    }
}
