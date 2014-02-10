package puzzle;
import static puzzle.EntityType.*;

import java.util.ArrayList;
/** Class that represents one solution of Entity Triples.
 * @author Daniel Paden Tomasello cs61b-bz. */
public class SolutionPermutation {
    /** ArrayList that holds EntityTriples of solution. */
    private ArrayList<EntityTriple> solution = new ArrayList<EntityTriple>();
    /** add Entity triple E to solution. */
    public void add(EntityTriple e) {
        solution.add(e);
    }
    /** return Entity triple at spot K. */
    public EntityTriple get(int k) {
        return solution.get(k);
    }
    /** Returns true if Solution associated E0 and E1. */
    public boolean isAssociated(NamedEntity e0, NamedEntity e1) {
        for (EntityTriple triple: solution) {
            if (triple.isTripleAssociated(e0, e1)) {
                return true;
            }
        }
        return false;
    }
    /** Returns Entitity triple if it contains E. */
    public EntityTriple aboutSingleEntity(NamedEntity e) {
        for (EntityTriple et: solution) {
            if (et.contains(e)) {
                return et;
            }
        }
        return null;
    }
    /** Returns true if Solution can be valid. */
    public boolean isValidSolution() {
        for (EntityTriple e: solution) {
            if (!e.isValidTriple()) {
                return false;
            }
        }
        for (int i = 0; i < solution.size(); i += 1) {
            for (int j = (i + 1); j < solution.size(); j += 1) {
                if (solution.get(i).hasSameEntities(solution.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
