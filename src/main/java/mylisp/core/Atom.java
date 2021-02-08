package mylisp.core;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Atom class
 *
 * @author moremagic
 */
public abstract class Atom implements Sexp {
    public abstract Object getValue();

    public abstract boolean equals(Object object);

    /**
     * TODO: メソッド名が気に入らないので変更する（候補；createAtom, makeAtom, getAtomInstance...)
     * @param value
     * @return
     * @throws AtomException
     */
    public static Atom newAtom(Object value) throws AtomException {
        if (value instanceof Boolean) {
            return AtomBoolean.createAtomBoolean(((Boolean) value).booleanValue());
        } else if (value instanceof Number) {
            return new AtomNumber((Number) value);
        } else if (value instanceof InputStream) {
            return new AtomPort((InputStream) value);
        } else if (value instanceof OutputStream) {
            return new AtomPort((OutputStream) value);
        } else if (value instanceof String) {
            String s = (String) value;

            if (AtomBoolean.isAtomBoolean(s)) {
                return AtomBoolean.createAtomBoolean(s);
            } else if (AtomNumber.isAtomNumber(s)) {
                return new AtomNumber(s);
            } else if (AtomString.isAtomString(s)) {
                return new AtomString(s);
            } else if (AtomChar.isAtomChar(s)) {
                return new AtomChar(s);
            } else if (AtomSymbol.isAtomSymbol(s)) {
                return new AtomSymbol(s);
            }
        }

        throw new Atom.AtomException(String.format("value is not %s syntax for [%s]", Atom.class.getName(), value));
    }

    /**
     * TODO: このメソッドがここにあることは正しい？評価器が持つべきでは？
     *
     * @return
     */
    @Override
    public Sexp[] getList() {
        return new Sexp[]{this};
    }

    /**
     * Atom生成時に発生したExceptionを表す
     */
    public static class AtomException extends MyLispException {
        public AtomException(String message) {
            super(message);
        }

        public AtomException(Exception e) {
            super(e);
        }
    }
}
