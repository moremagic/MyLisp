package mylisp.core;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Atom class
 *
 * @author moremagic
 */
public abstract class Atom implements Sexp {

    private static final Pattern pattern = java.util.regex.Pattern.compile("^\\-?[0-9]*\\.?[0-9]+$");

    public abstract Object getValue();

    public static Atom newAtom(Object value) {
        if (value instanceof Boolean){
            return AtomBoolean.createAtomBoolean(((Boolean)value).booleanValue());
        }else if (value.toString().equals(AtomBoolean.T) || value.toString().equals(AtomBoolean.F)) {
            return AtomBoolean.createAtomBoolean(value.toString().equals(AtomBoolean.T));
        } else if (value instanceof Number) {
            return new AtomNumber((Number) value);
        } else {
            Number num = getNumber(value.toString());
            if (num == null) {
                return new AtomSymbol(value.toString());
            } else {
                return new AtomNumber(num);
            }
        }
    }

    private static Number getNumber(String s) {
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches()) {
            try {
                if (s.indexOf(".") != -1) {
                    return new Double(s);
                } else {
                    return new Integer(s);
                }
            } catch (NumberFormatException err) {
                return new BigDecimal(new Double(s));
            }
        }

        return null;
    }
    
    /**
     * Atom Equrls
     * @param atom
     * @return
     */
    abstract public boolean equalAtom(Atom atom);
}
