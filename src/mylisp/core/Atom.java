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
 * @author mitsu
 */
public abstract class Atom implements Sexp {

    private static final Pattern pattern = java.util.regex.Pattern.compile("^\\-?[0-9]*\\.?[0-9]+$");

    public abstract Object getValue();

    public static Atom newAtom(Object value) {
        if (value.toString().equals("#t") || value.toString().equals("#f")) {
            return new AtomBoolean(value.toString());
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
            }
            try {
                return new BigDecimal(new Double(s));
            } catch (NumberFormatException err) {
            }
        }

        return null;
    }
}
