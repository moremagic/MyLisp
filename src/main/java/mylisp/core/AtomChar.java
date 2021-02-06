package mylisp.core;

/**
 * Char Atom
 * //TODO: char 型を扱うのではなく、一文字を扱うように変更をすると便利かも
 *
 * @author moremagic
 */
public class AtomChar extends Atom {
    private static final String PREFIX = "#\\";
    private static final String SPACE = "space";
    private static final String TAB = "tab";
    private static final String NEWLINE = "newline";
    private char value;

    AtomChar(String value) throws AtomException {
        if (!value.startsWith(PREFIX)) {
            throw new Atom.AtomException(String.format("value is not %s syntax for [%s]", getClass().getName(), value));
        }

        if (value.equals(PREFIX + SPACE)) {
            this.value = ' ';
        } else if (value.equals(PREFIX + TAB)) {
            this.value = '\t';
        } else if (value.equals(PREFIX + NEWLINE)) {
            this.value = '\n';
        } else {
            this.value = value.substring(PREFIX.length()).charAt(0);
        }
    }

    public AtomChar(char value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return new String(new char[]{this.value});
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof AtomChar) {
            return ((AtomChar) object).value == value;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.value;
        return hash;
    }
}
