package mylisp.core;

/**
 * symbol Atom
 *
 * @author moremagic
 */
public class AtomSymbol extends Atom {
    private String value;

    AtomSymbol(String value) throws AtomException {
        if (!isAtomSymbol(value)) {
            throw new Atom.AtomException(String.format("value is not %s syntax for [%s]", getClass().getName(), value));
        }

        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof AtomSymbol) {
            return ((AtomSymbol) object).value.equals(value);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }

    /**
     * AtomSymbol表現かを検査する
     *
     * @param value 検査したいString
     * @return AtomSymbolであればTrue
     */
    public static boolean isAtomSymbol(String value) {
        return !AtomString.isAtomString(value) && !AtomChar.isAtomChar(value);
    }
}
