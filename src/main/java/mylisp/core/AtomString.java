package mylisp.core;

/**
 * String Atom
 *
 * @author moremagic
 */
public class AtomString extends Atom {
    private final String value;

    AtomString(String value) throws AtomException {
        if (!value.startsWith("\"") || !value.endsWith("\"")) {
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
        if (object instanceof AtomString) {
            return ((AtomString) object).value.equals(value);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }
}
