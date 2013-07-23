package mylisp.core;

import java.math.BigDecimal;
import java.math.BigInteger;
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

    private static final Pattern pattern_number = java.util.regex.Pattern.compile("^\\-?[0-9]*\\.?[0-9]+$");
    private static final Pattern pattern_string = java.util.regex.Pattern.compile("^\".*\"$");

    public abstract Object getValue();

    public static Atom newAtom(Object value) {
        if (value instanceof Boolean) {
            return AtomBoolean.createAtomBoolean(((Boolean) value).booleanValue());
        } else if (value.toString().equals(AtomBoolean.T) || value.toString().equals(AtomBoolean.F)) {
            return AtomBoolean.createAtomBoolean(value.toString().equals(AtomBoolean.T));
        } else if (value instanceof Number) {
            return new AtomNumber((Number) value);
        } else {
            Number num = getNumber(value.toString());
            if (num == null) {
                String str = getString(value.toString());
                if (str == null) {
                    return new AtomSymbol(value.toString());
                } else {
                    return new AtomString(value.toString());
                }
            } else {
                return new AtomNumber(num);
            }
        }
    }

    private static Number getNumber(String s) {
        Matcher matcher = pattern_number.matcher(s);
        if (matcher.matches()) {
            if (s.indexOf(".") != -1) {
                try {
                    return new Double(s);
                } catch (NumberFormatException err) {
                    return new BigDecimal(new Double(s));
                }
            } else {
                try {
                    return new Integer(s);
                } catch (NumberFormatException err) {
                    return new BigInteger(s);
                }
            }
        }

        return null;
    }

    private static String getString(String s) {
        Matcher matcher = pattern_string.matcher(s);
        if (matcher.matches()) {
            return s;
        }
        return null;
    }
}
