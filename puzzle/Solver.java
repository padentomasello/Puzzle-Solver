package puzzle;

import java.util.ArrayList;
import static puzzle.EntityType.*;

/** A puzzle-solving engine.
    @author Daniel Paden Tomasello cs61b-bz */
class Solver {
    /** ArrayList holding people entities. */
    private ArrayList<NamedEntity> people = new ArrayList<NamedEntity>();
    /** ArrayList holding occupation entities. */
    private ArrayList<NamedEntity> jobs = new ArrayList<NamedEntity>();
    /** ArrayList holding color entities. */
    private ArrayList<NamedEntity> houses = new ArrayList<NamedEntity>();
    /** ArrayLIst holding possible permutations. */
    private ArrayList<SolutionPermutation> permutations;
    /**ArrayList containing invalid NamedEntity names. */
    private ArrayList<String> invalidNames = new ArrayList<String>(0);
    /** Solver contructor which adds invalid names and
     * instantiates permutations.
     */
    Solver() {
        invalidNames.add("Who");
        invalidNames.add("There");
        invalidNames.add("The");
        invalidNames.add("occupant");
        permutations = new ArrayList<SolutionPermutation>();
    }
    /** Asserts that E0 and E1 are associated (e.g., Tom is the
     *  carpenter). */
    void associate(NamedEntity e0, NamedEntity e1) {
        SolutionPermutation temp = null;
        for (SolutionPermutation solution: permutations) {
            if (!solution.isAssociated(e0, e1)) {
                temp = solution;
                break;
            }
        }
        if (temp != null) {
            permutations.remove(temp);
            associate(e0, e1);
        }
    }

    /** Asserts that E0 and E1 are not associated (e.g., Tom is not the
     *  carpenter). */
    void disassociate(NamedEntity e0, NamedEntity e1) {
        SolutionPermutation temp = null;
        for (SolutionPermutation solution: permutations) {
            if (solution.isAssociated(e0, e1)) {
                temp = solution;
                break;
            }
        }
        if (temp != null) {
            permutations.remove(temp);
            disassociate(e0, e1);
        }
    }

    /** Asserts that E exists (e.g., Tom lives around here). */
    void exists(NamedEntity e) {
        if (e.getType() == PERSON & !people.contains(e)) {
            for (String string: invalidNames) {
                if (string.equals(e.getName())) {
                    throw new PuzzleException();
                }
            }
            people.add(e);
            invalidNames.add(e.getName());
        }
        if (e.getType() == OCCUPATION & !jobs.contains(e)) {
            for (String string: invalidNames) {
                if (string.equals(e.getName())) {
                    throw new PuzzleException();
                }
            }
            jobs.add(e);
            invalidNames.add(e.getName());
        }
        if (e.getType() == COLOR & !houses.contains(e)) {
            for (String string: invalidNames) {
                if (string.equals(e.getName())) {
                    throw new PuzzleException();
                }
            }
            houses.add(e);
            invalidNames.add(e.getName());
        }
    }

    /** Return a list of all possible associations with E of entities that
     * include
     *  the entity named ID.  For example, knownAbout("Tom") might
     *  contain a list of the lists:
     *     [ (Tom, PERSON), (plumber, OCCUPATION), (green, COLOR) ],
     *     [ (Tom, PERSON), (plumber, OCCUPATION), (color#1, COLOR) ]
     *  (where color#1 denotes an anonymous color.). */
    public ArrayList<EntityTriple> knownAbout(NamedEntity e) {
        ArrayList<EntityTriple> result = new ArrayList<EntityTriple>();
        for (SolutionPermutation solution: permutations) {
            result.add(solution.aboutSingleEntity(e));
        }
        return result;
    }
    /** Returns all information about E if available or null. */
    public EntityTriple allInfo(NamedEntity e) {
        ArrayList<EntityTriple> solutions = knownAbout(e);
        EntityTriple temp = solutions.get(0);
        if (NamedEntity.isAnonymous(temp.name().getName())) {
            return null;
        }
        if (NamedEntity.isAnonymous(temp.color().getName())) {
            return null;
        }
        if (NamedEntity.isAnonymous(temp.job().getName())) {
            return null;
        }
        for (int i = 0; i < solutions.size(); i += 1) {
            if (!temp.equals(solutions.get(i))) {
                return null;
            }
        }
        return temp;
    }
    /** Returns Occupation of E if availabe or null. */
    public NamedEntity getJob(NamedEntity e) {
        ArrayList<EntityTriple> solutions = knownAbout(e);
        NamedEntity temp = solutions.get(0).job();
        if (NamedEntity.isAnonymous(temp.getName())) {
            return null;
        }
        for (int i = 1; i < solutions.size(); i += 1) {
            if (!temp.equals(solutions.get(i).job())) {
                return null;
            }
        }
        return temp;
    }
    /** Returns Color of E if available or null. */
    public NamedEntity getColor(NamedEntity e) {
        ArrayList<EntityTriple> solutions = knownAbout(e);
        NamedEntity temp = solutions.get(0).color();
        if (NamedEntity.isAnonymous(temp.getName())) {
            return null;
        }
        for (int i = 1; i < solutions.size(); i += 1) {
            if (!temp.equals(solutions.get(i).color())) {
                return null;
            }
        }
        return temp;
    }
    /** Returns Person associated with E or null. */
    public NamedEntity getPeople(NamedEntity e) {
        ArrayList<EntityTriple> solutions = knownAbout(e);
        NamedEntity temp = solutions.get(0).name();
        if (NamedEntity.isAnonymous(temp.getName())) {
            return null;
        }
        for (int i = 1; i < solutions.size(); i += 1) {
            if (!temp.equals(solutions.get(i).name())) {
                return null;
            }
        }
        return temp;
    }
    /** Return true iff the current set of facts is impossible. */
    boolean impossible() {
        if (permutations.size() == 0) {
            return true;
        }
        return false;
    }
    /** Returns an annoynmous NamedEntity if it exists in L. If it does,
     * it replaces Annonymous Entity with nonannonymous entity.
     * If not, it adds entity E to list.   */
    public NamedEntity checkAnnonymous(ArrayList<NamedEntity> L,
            NamedEntity e) {
        for (int i = 0; i < L.size(); i += 1) {
            if (L.get(i).isAnonymous()) {
                NamedEntity x = L.get(i);
                L.set(i, e);
                return x;
            }
        }
        L.add(e);
        return null;
    }
    /** Ensures that all Entity type arrays are the same length by adding
     * annonymous Entity types to the shorter.
     */
    public void addAnnonymous() {
        int x = Math.max(people.size(), jobs.size());
        x = Math.max(x, houses.size());
        while (houses.size() < x) {
            houses.add(new NamedEntity(NamedEntity.makeName("color"), COLOR));
        }
        while (people.size() < x) {
            people.add(new NamedEntity(NamedEntity.makeName("person"), PERSON));
        }
        while (jobs.size() < x) {
            jobs.add(new NamedEntity(NamedEntity.makeName("occupation"),
                    OCCUPATION));
        }
    }
    /** Builds all possible permutations and adds them to array
     * permutations. */
    public void buildPermutation() throws PuzzleException {
        addAnnonymous();
        int maxSize = people.size();
        PermNum permNum = new PermNum(maxSize, 3 * maxSize);
        for (int i = 0; i < permNum.numIncr(); i += 1) {
            SolutionPermutation perm = new SolutionPermutation();
            for (int x = 0; x < (maxSize * 3); x += 3) {
                perm.add(new EntityTriple(people.get(permNum.get(x)),
                        jobs.get(permNum.get(x + 1)),
                        houses.get(permNum.get(x + 2))));
            }
            permNum.increment(0);
            if (perm.isValidSolution()) {
                permutations.add(perm);
            }

        }
    }
    /** Returns people ArrayList. */
    public ArrayList<NamedEntity> getPeople() {
        return people;
    }
    /** Returns jobs ArrayList. */
    public ArrayList<NamedEntity> getJobs() {
        return jobs;
    }
    /** Returns houses ArrayList. */
    public ArrayList<NamedEntity> getHouses() {
        return houses;
    }
    /** Returns permutations ArrayList. */
    public ArrayList<SolutionPermutation> getPermutations() {
        return permutations;
    }
    /** Returns invalidNames ArrayList. */
    public ArrayList<String> getInvalidNames() {
        return invalidNames;
    }
    /** Sets people ArrayList to PEOPLE1. */
    public void setPeople(ArrayList<NamedEntity> people1) {
        this.people = people1;
    }
    /** Sets jobs ArrayList to JOBS1. */
    public void setJobs(ArrayList<NamedEntity> jobs1) {
        this.jobs = jobs1;
    }
    /** Sets houses ArrayList to HOUSES1. */
    public void setHouses(ArrayList<NamedEntity> houses1) {
        this.houses = houses1;
    }
    /** Sets permutations ArrayList to PERMUTATIONS1. */
    public void setPermutations(ArrayList<SolutionPermutation> permutations1) {
        this.permutations = permutations1;
    }
    /** Sets inValidNames ArrayList to INVALIDNAMES1. */
    public void setInvalidNames(ArrayList<String> invalidNames1) {
        this.invalidNames = invalidNames1;
    }
}
