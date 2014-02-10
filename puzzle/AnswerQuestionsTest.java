package puzzle;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.StringReader;

/** Tests of Parser class.
 *  @author */
public class AnswerQuestionsTest {

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
    public void test() throws PuzzleException {
        setUp("John is not the carpenter.  The "
                + "plumber lives in the blue house.",
                "John lives in the yellow house.",
                "Mary does not live in the blue"
                 + " house.  Tom lives around here.",
                "The architect lives around here.",
                "What do you know about John?",
                "What do you know about Mary?", "Who is the carpenter?",
                "Where does John live?");
        Parser p = Parser.parse(reader);
        Solver solver = new Solver();
        p.inform(solver);
        assertEquals("Does not answer question "
                + "correctly", "A: John is the arc"
                + "hitect and lives in the yellow house."
                , p.getAnswer(solver, 0));
        assertEquals("Does not answer question "
                + "correctly", "A: John lives in the "
                        + "yellow house.", p.getAnswer(solver, 3));
    }
}


