package puzzle;
import static puzzle.EntityType.*;
/** Class that represents 3 NamedEntities, consisting
 * of a person, occupation and house color.
 * @author Daniel Paden Tomasello cs61b-bz
 *
 */
public class EntityTriple {
    /** Variables to reference PERSON, OCCUPATION and COLOR. */
    private NamedEntity _name, _job, _color;
    /** Constructor takes in three Named Entitier, where NAME is a person
     * JOB is a occupation and COLOR is a house color and saves them as
     * instance variables of object.
     */
    EntityTriple(NamedEntity name, NamedEntity job, NamedEntity color) {
        _name = name;
        _job = job;
        _color = color;
    }
    /** Method takes E as arguement and returns if the Entity triple contains
     * same Named Entities.
     */
    public boolean equals(EntityTriple e) {
        if (_name.equals(e.name()) & _job.equals(e.job())
                &  _color.equals(e.color())) {
            return true;
        }
        return false;
    }
    /** Returns a hashcode for me, allowing EntityTriples to be used
    in HashSets and HashMaps. */
    public int hashCode() {
        return _name.hashCode() * _job.hashCode() * _color.hashCode();
    }
    /** Returns true if I contain NamedEntity E. */
    public boolean contains(NamedEntity e) {
        if (_name.equals(e) | _job.equals(e) | _color.equals(e)) {
            return true;
        }
        return false;
    }
    /** Returns the PERSON of me. */
    public NamedEntity name() {
        return _name;
    }
    /** Returns the OCCUPATION of me. */
    public NamedEntity job() {
        return _job;
    }
    /** Returns the COLOR of me. */
    public NamedEntity color() {
        return _color;
    }
    /** Returns true if I contain both E0 and E1. */
    public boolean isTripleAssociated(NamedEntity e0, NamedEntity e1) {
        if (e0.equals(_name) & e1.equals(_job)) {
            return true;
        }
        if (e0.equals(_name) & e1.equals(_color)) {
            return true;
        }
        if (e0.equals(_job) & e1.equals(_color)) {
            return true;
        }
        return false;
    }
    /** Returns true if each Named Entity is of correct type. */
    public boolean isValidTriple() {
        if (_name.getType() == PERSON
            & _job.getType() == OCCUPATION
            & _color.getType() == COLOR) {
            return true;
        }
        return false;
    }
    /** Returns true if I am identical to E. */
    public boolean hasSameEntities(EntityTriple e) {
        if (_name.equals(e.name())
            | _job.equals(e.job())
            | _color.equals(e.color())) {
            return true;
        }
        return false;
    }
}


