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

    private final boolean value;

    private AtomBoolean(String value) {
        this.value = value.equals(T);
    }

    public static AtomBoolean createAtomBoolean(boolean value) {
        return value ? AtomTrue : AtomFalse;
    }

    public static AtomBoolean createAtomBoolean(String value) {
        return value.equals(T) ? AtomTrue : AtomFalse;
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

    /**
     * AtomBoolean表現かを検査する
     *
     * @param value 検査したいString
     * @return AtomBooleanであればTrue
     */
    public static boolean isAtomBoolean(String value) {
        return value.equals(T) || value.equals(F);
    }
}
