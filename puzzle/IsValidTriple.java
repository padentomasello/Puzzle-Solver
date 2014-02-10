package puzzle;

import static org.junit.Assert.*;
import static puzzle.EntityType.*;
import org.junit.Test;
/** Tests isValidEntity and isValidSolution.
 * @author Daniel Paden Tomasello cs61b-bz. */
public class IsValidTriple {
    /** Well, this is the test. */
    @Test
    public void test() {
        EntityTriple triple, triple1, badtriple;
        NamedEntity a = new NamedEntity("Paden", PERSON);
        NamedEntity b = new NamedEntity("Sailor", OCCUPATION);
        NamedEntity x = new NamedEntity("red", COLOR);
        NamedEntity a1 = new NamedEntity("John", PERSON);
        NamedEntity b1 = new NamedEntity("carpenter", OCCUPATION);
        NamedEntity x1 = new NamedEntity("blue", COLOR);
        triple = new EntityTriple(a, b, x);
        triple1 = new EntityTriple(a1, b1, x1);
        badtriple = new EntityTriple(a, a, x);
        assertEquals("IsValidEntity is working incorrectly",
                true, triple.isValidTriple());
        assertEquals("IsValidEntity is working incorrectly",
                false, badtriple.isValidTriple());
        SolutionPermutation y, z, p;
        z = new SolutionPermutation();
        y = new SolutionPermutation();
        p = new SolutionPermutation();
        y.add(triple);
        y.add(triple);
        assertEquals("IsValidSolution is working incorrectly",
                false, y.isValidSolution());
        z.add(triple1);
        z.add(triple);
        assertEquals("IsValidSolution is working incorrectly",
                true , z.isValidSolution());
        p.add(triple);
        p.add(badtriple);
        assertEquals("IsValidSolution is working incorrectly",
                false, y.isValidSolution());
    }
}
