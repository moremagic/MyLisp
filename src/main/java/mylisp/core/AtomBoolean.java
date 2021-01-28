package mylisp.core;

/**
 * boolean atom class
 *
 * @author moremagic
 */
public class AtomBoolean extends Atom {
    static final String T = "#t";
    static final String F = "#f";
    public static final AtomBoolean AtomTrue = new AtomBoolean(T);
    public static final AtomBoolean AtomFalse = new AtomBoolean(F);

    private boolean value;

    private AtomBoolean(String value) {
        this.value = value.equals(T) ? true : false;
    }

    public static AtomBoolean createAtomBoolean(boolean b) {
        return b ? AtomTrue : AtomFalse;
    }

    @Override
    public Object getValue() {
        return value ? T : F;
    }

    @Override
    public String toString() {
        return value ? T : F;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof AtomBoolean) {
            return ((AtomBoolean) object).value == value;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (this.value ? 1 : 0);
        return hash;
    }
}
