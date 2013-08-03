package mylisp.core;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Char Atom
 *
 * @author moremagic
 */
public class AtomChar extends Atom {
    private static final String SPACE = "space";
    private static final String TAB = "tab";
    private static final String NEWLINE = "newline";
    private char value;

    AtomChar(String value) {
        if (value.startsWith("#\\")) {
            value = value.substring(2);
        }

        //TODO: マクロで実装すべき？？
        if (value.equals(SPACE)) {
            this.value = ' ';
        } else if (value.equals(TAB)) {
            this.value = '\t';
        } else if (value.equals(NEWLINE)) {
            this.value = '\n';
        } else {
            this.value = value.charAt(0);
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
