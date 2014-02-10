package puzzle;
import org.junit.Test;
import static org.junit.Assert.*;
import static puzzle.EntityType.*;
import java.io.StringReader;
import java.util.ArrayList;

/** Tests of Parser class.
 *  @author Daniel Paden Tomasello cs61b-bz */
public class KnownAboutTest {

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
        setUp("Jack lives in the blue house.  Mary does not        "
                + "live in the blue house.",
                "The mechanic lives around here.",
                "There is a red house.  The architect lives around here.",
                "The sailor lives around here.");
        Parser p = Parser.parse(reader);
        Solver solver = new Solver();
        p.inform(solver);
        ArrayList<EntityTriple> a;
        a = solver.knownAbout(new NamedEntity("Jack", PERSON));
        System.out.println(a.size());
        System.out.println(a.get(0).name());
        assertEquals("knownAbout is working incorrectly", 72, a.size());
        assertEquals("knownAbout is working incorrectly",
                new NamedEntity("Jack", PERSON), a.get(0).name());
    }
}


