package puzzle;
import static puzzle.EntityType.COLOR;
import static puzzle.EntityType.OCCUPATION;
import static puzzle.EntityType.PERSON;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.MatchResult;

/** A sequence of Assertions and Questions parsed from a given file.
 *  @author Daniel Paden Tomasello cs61b-bz */
class Parser {
    /** String pattern representing Statement #1. */
    private static final String STATE1 = "([A-Z][a-z]*)?(The\\s+([a-z]+))?\\s+"
             + "(lives\\s+in|does\\s+not\\s+live\\s+in)\\s+the\\s+([a-z]+)\\s+"
             + "house\\.";
    /** String pattern representing Statement #2. */
    private static final String STATE2 = "([A-Z][a-z]*)\\s+(is|is\\s+not)\\s+"
            + "the\\s+([a-z]*)\\.";
    /** String pattern representing Statement #3. */
    private static final String STATE3 = "([A-Z][a-z]*)?(The\\s+([a-z]+))?"
            + "\\s+lives\\s+around\\s+here\\.";
    /** String pattern representing Statement #4. */
    private static final String STATE4 = "There\\s+is\\s+a\\s+([a-z]+)"
                + "\\s+house\\.";
    /** String pattern representing Question #1. */
    private static final String QUEST1 = "What\\s+do\\s+you\\s+know\\s+"
            + "about\\s+([A-Z][a-z]*)?(the\\s+([a-z]+))?\\?";
    /** String pattern representing Question #2. */
    private static final String QUEST2 = "What\\s+do\\s+you\\s+know\\s+about"
            + "\\s+the\\s+([a-z]+)\\s+house\\?";
    /** String pattern representing Question #3. */
    private static final String QUEST3 = "Who\\s+is\\s+the\\s+([a-z]+)\\?";
    /** String pattern representing Question#4. */
    private static final String QUEST4 = "Who\\s+lives\\s+in\\s+the\\s+"
            + "([a-z]+)\\s+house\\?";
    /** String pattern representing Question#5. */
    private static final String QUEST5 = "What\\s+does\\s+the\\s+occupant"
            + "\\s+of\\s+the\\s+([a-z]+)\\s+house+\\s+do\\?";
    /** String pattern representing Question#6. */
    private static final String QUEST6 = "What\\s+does\\s+([A-Z][a-z]*)\\s"
            + "+do\\?";
    /** String pattern representing Question#7. */
    private static final String QUEST7 = "Where\\s+does\\s+([A-Z][a-z]*)?"
            + "(the\\s+([a-z]+))?\\s+live\\?";
    /** String Array containing all State Pattern. */
    private static final String[] STATEARRAY = {STATE1, STATE2, STATE3, STATE4};
    /** String Array containing all Question patterns.*/
    private static final String[] QUESTARRAY = {QUEST1, QUEST2, QUEST3,
        QUEST4, QUEST5, QUEST6, QUEST7};
    /** ArrayList containing all statements. */
    private ArrayList<String> stateAsStrings = new ArrayList<String>(0);
    /** ArrayList containing all questions. */
    private ArrayList<String> questAsStrings = new ArrayList<String>(0);
    /** A new Parser, containing no assertions or questions. */
    private Parser() {
    }
    /** Returns a Parser that contains assertions and questions from
     *  READER. */
    static Parser parse(Reader reader) {
        Scanner inp = new Scanner(reader);
        Parser result = new Parser();
        result.setUp(inp);
        return result;
    }
    /** Sets up individual parser object with INP scanner. */
    public void setUp(Scanner inp) {
        String x = null;
        while (inp.hasNext()) {
            x = inp.findWithinHorizon(".*?(\\.|\\?)", 0);
            if (x == null) {
                throw new PuzzleException();
            }
            x = x.replaceAll("\\s+", " ").trim();
            if (inp.match().group(1).equals(".")) {
                if (questAsStrings.size() != 0) {
                    throw new PuzzleException();
                }
                stateAsStrings.add(x);
            } else {
                questAsStrings.add(x);
            }
        }
        if (stateAsStrings.size() == 0) {
            throw new PuzzleException();
        }
    }
    /** Transfer all information conveyed by the asserations I have
     *  read to SOLVER. */
    void inform(Solver solver) throws PuzzleException {
        String finalMatch = null;
        MatchResult groups = null;
        compileExistingEntities(solver);
        solver.buildPermutation();
        int i = 1;
        for (String statement: stateAsStrings) {
            System.out.println(i + ". " +  statement);
            i += 1;
            Scanner stateScanner = new Scanner(statement);
            for (String match: STATEARRAY) {
                if (statement.matches(match)) {
                    finalMatch = match;
                    stateScanner.findInLine(match);
                    groups = stateScanner.match();
                    break;
                }
            }
            NamedEntity e0, e1;
            if (finalMatch == STATE1) {
                if (groups.group(1) == null) {
                    e0 = new NamedEntity(groups.group(3), OCCUPATION);
                    e1 = new NamedEntity(groups.group(5), COLOR);
                } else {
                    e0 = new NamedEntity(groups.group(1), PERSON);
                    e1 = new NamedEntity(groups.group(5), COLOR);
                }
                if (groups.group(4).equals("lives in")) {
                    solver.associate(e0, e1);
                } else {
                    solver.disassociate(e0, e1);
                }
            }
            if (finalMatch == STATE2) {
                e0 = new NamedEntity(groups.group(1), PERSON);
                e1 = new NamedEntity(groups.group(3), OCCUPATION);
                if (groups.group(2).equals("is")) {
                    solver.associate(e0, e1);
                } else {
                    solver.disassociate(e0, e1);
                }
            }
        }
        System.out.println("");
    }
    /** Checks statements and commands SOLVER to build all necessary
     * permutations. */
    public void compileExistingEntities(Solver solver) {
        String finalMatch = null;
        MatchResult groups = null;
        for (String statement: stateAsStrings) {
            Scanner stateScanner = new Scanner(statement);
            for (String match: STATEARRAY) {
                if (statement.matches(match)) {
                    finalMatch = match;
                    stateScanner.findInLine(match);
                    groups = stateScanner.match();
                    break;
                }
            }
            if (finalMatch == null) {
                throw new PuzzleException("Statement does not match format");
            }
            NamedEntity name = null, occupation = null, color = null;
            if (finalMatch == STATE1) {
                if (groups.group(1) == null) {
                    occupation = new NamedEntity(groups.group(3), OCCUPATION);
                    color = new NamedEntity(groups.group(5), COLOR);
                    solver.exists(occupation);
                    solver.exists(color);
                } else {
                    name = new NamedEntity(groups.group(1), PERSON);
                    color = new NamedEntity(groups.group(5), COLOR);
                    solver.exists(name);
                    solver.exists(color);
                }
            }
            if (finalMatch == STATE2) {
                name = new NamedEntity(groups.group(1), PERSON);
                occupation = new NamedEntity(groups.group(3), OCCUPATION);
                solver.exists(name);
                solver.exists(occupation);
            }
            if (finalMatch == STATE3) {
                if (groups.group(2) == null) {
                    name = new NamedEntity(groups.group(1), PERSON);
                    solver.exists(name);
                } else {
                    occupation = new NamedEntity(groups.group(3), OCCUPATION);
                    solver.exists(occupation);
                }
            }
            if (finalMatch == STATE4) {
                color = new NamedEntity(groups.group(1), COLOR);
                solver.exists(color);
            }
        }
    }
    /** Returns the number of assertions I have parsed. */
    int numAssertions() {
        return stateAsStrings.size();
    }

    /** Returns the text of assertion number K (numbering from 0), with extra
     *  spaces removed. */
    String getAssertion(int k) {
        return stateAsStrings.get(k);
    }


    /** Returns the number of questions I have parsed. */
    int numQuestions() {
        return questAsStrings.size();
    }

    /** Return the text of question number K (numbering from 0), with extra
     *  spaces removed. */
    String getQuestion(int k) {
        return questAsStrings.get(k);
    }

    /** Return the answer to question K, according to the information
     *  in SOLVER. */
    String getAnswer(Solver solver, int k) {
        String finalMatch = null;
        MatchResult groups = null;
        String question = getQuestion(k);
        System.out.println("Q: " + question);
        Scanner questScanner = new Scanner(question);
        for (String match: QUESTARRAY) {
            if (question.matches(match)) {
                finalMatch = match;
                questScanner.findInLine(match);
                groups = questScanner.match();
                break;
            }
        }
        return retrieveAns(finalMatch, groups, solver);
    }
    /** Uses FINALMATCH, GROUPS AND SOLVER to find possible answer formats and
     * outputs correct format.
     * @return formatted String answer
     */
    String retrieveAns(String finalMatch, MatchResult groups, Solver solver) {
        String a = matchQuest1(finalMatch, groups, solver);
        String b =  matchQuest1(finalMatch, groups, solver);
        String c = matchQuest2(finalMatch, groups, solver);
        String d = matchQuest3(finalMatch, groups, solver);
        String e = matchQuest4(finalMatch, groups, solver);
        String f = matchQuest5(finalMatch, groups, solver);
        String g = matchQuest6(finalMatch, groups, solver);
        String h = matchQuest7(finalMatch, groups, solver);
        String[] strings = {a, b, c, d, e, f, g, h};
        for (String string: strings) {
            if (string != null) {
                return string;
            }
        }
        throw new PuzzleException();
    }
    /** Checks if FINALMATCH is of QUEST1, and gives answer using GROUPS
     * and SOLVER.
     * @return formatted string answer
     */
    String matchQuest1(String finalMatch, MatchResult groups, Solver solver) {
        NamedEntity e, b;
        EntityTriple a;
        if (finalMatch == QUEST1) {
            if (groups.group(1) != null) {
                e = new NamedEntity(groups.group(1), PERSON);
                if (!solver.getPeople().contains(e)) {
                    throw new PuzzleException();
                }
            } else {
                e = new NamedEntity(groups.group(3), OCCUPATION);
                if (!solver.getJobs().contains(e)) {
                    throw new PuzzleException();
                }
            }
            a = solver.allInfo(e);
            if (a != null) {
                return livesInAndOcc(a);
            }
            b = solver.getColor(e);
            if (b != null) {
                return livesIn(e, b);
            }
            b = solver.getJob(e);
            if (b != null) {
                return isOcc(e, b);
            }
            return nothing();
        }
        return null;
    }
    /** Checks if FINALMATCH is of QUEST2, and gives answer using GROUPS
     * and SOLVER.
     * @return formatted string answer
     */
    String matchQuest2(String finalMatch, MatchResult groups, Solver solver) {
        NamedEntity e, b;
        EntityTriple a;
        if (finalMatch == QUEST2) {
            e = new NamedEntity(groups.group(1), COLOR);
            if (!solver.getHouses().contains(e)) {
                throw new PuzzleException();
            }
            a = solver.allInfo(e);
            if (a != null) {
                return livesInAndOcc(a);
            }
            b = solver.getPeople(e);
            if (b != null) {
                return livesIn(b, e);
            }
            b = solver.getJob(e);
            if (b != null) {
                return livesIn(b, e);
            }
            return nothing();
        }
        return null;
    }
    /** Checks if FINALMATCH is of QUEST3, and gives answer using GROUPS
     * and SOLVER.
     * @return formatted string answer
     */
    String matchQuest3(String finalMatch, MatchResult groups, Solver solver) {
        NamedEntity e, b;
        if (finalMatch == QUEST3) {
            e = new NamedEntity(groups.group(1), OCCUPATION);
            if (!solver.getJobs().contains(e)) {
                throw new PuzzleException();
            }
            b = solver.getPeople(e);
            if (b != null) {
                return isOcc(b, e);
            }
            return iDontKnow();
        }
        return null;
    }
    /** Checks if FINALMATCH is of QUEST4, and gives answer using GROUPS
     * and SOLVER.
     * @return formatted string answer
     */
    String matchQuest4(String finalMatch, MatchResult groups, Solver solver) {
        NamedEntity e, b;
        if (finalMatch == QUEST4) {
            e = new NamedEntity(groups.group(1), COLOR);
            if (!solver.getHouses().contains(e)) {
                throw new PuzzleException();
            }
            b = solver.getPeople(e);
            if (b != null) {
                return livesIn(b, e);
            }
            return iDontKnow();
        }
        return null;
    }
    /** Checks if FINALMATCH is of QUEST5, and gives answer using GROUPS
     * and SOLVER.
     * @return formatted string answer
     */
    String matchQuest5(String finalMatch, MatchResult groups, Solver solver) {
        NamedEntity e, c;
        if (finalMatch == QUEST5) {
            e = new NamedEntity(groups.group(1), COLOR);
            if (!solver.getHouses().contains(e)) {
                throw new PuzzleException();
            }
            c = solver.getJob(e);
            if (c != null) {
                return livesIn(c, e);
            }
            return iDontKnow();
        }
        return null;
    }
    /** Checks if FINALMATCH is of QUEST6, and gives answer using GROUPS
     * and SOLVER.
     * @return formatted string answer
     */
    String matchQuest6(String finalMatch, MatchResult groups, Solver solver) {
        NamedEntity e, b;
        if (finalMatch == QUEST6) {
            e = new NamedEntity(groups.group(1), PERSON);
            if (!solver.getPeople().contains(e)) {
                throw new PuzzleException();
            }
            b = solver.getJob(e);
            if (b != null) {
                return isOcc(e, b);
            }
            return iDontKnow();
        }
        return null;
    }
    /** Checks if FINALMATCH is of QUEST7, and gives answer using GROUPS
     * and SOLVER.
     * @return formatted string answer
     */
    String matchQuest7(String finalMatch, MatchResult groups, Solver solver) {
        NamedEntity e, b;
        if (finalMatch == QUEST7) {
            if (groups.group(2) == null) {
                e = new NamedEntity(groups.group(1), PERSON);
                if (!solver.getPeople().contains(e)) {
                    throw new PuzzleException();
                }
            } else {
                e = new NamedEntity(groups.group(3), OCCUPATION);
                if (!solver.getJobs().contains(e)) {
                    throw new PuzzleException();
                }
            }
            b = solver.getColor(e);
            if (b != null) {
                return livesIn(e, b);
            }
            return iDontKnow();
        }
        return null;
    }
    /** Returns a formated answer of Nothing. */
    public String nothing() {
        return ("A: Nothing.");
    }
    /** Returns a formated answer of I don't know. */
    public String iDontKnow() {
        return ("A: I don't know.");
    }
    /** Returns a formated live in string answer using E0 and E1. */
    public String livesIn(NamedEntity e0, NamedEntity e1) {
        if (e0.getType() == PERSON) {
            return ("A: " + e0.getName() + " lives in the "
                + e1.getName() + " house.");
        }
        return ("A: The " + e0.getName() + " lives in the "
            + e1.getName() + " house.");
    }
    /** Returns a formated live in and has occ answer using
     * named entities E. */
    public String livesInAndOcc(EntityTriple e) {
        return ("A: " + e.name().getName() + " is the " + e.job().getName()
            + " and lives in the " + e.color().getName() + " house.");
    }
    /** Returns a formatted is occupation answer using named
     * entities E0 and E1. */
    public String isOcc(NamedEntity e0, NamedEntity e1) {
        return ("A: " + e0.getName() + " is the " + e1.getName() + ".");
    }
}


