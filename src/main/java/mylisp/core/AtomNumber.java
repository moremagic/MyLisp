package mylisp.core;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author moremagic
 */
public class AtomNumber extends Atom {
    private static final Pattern pattern_number = java.util.regex.Pattern.compile("^-?[0-9]*\\.?[0-9]+$");
    private Number value;

    AtomNumber(String value) throws AtomException {
        if (!isAtomNumber(value)) {
            throw new Atom.AtomException(String.format("value is not %s syntax for [%s]", getClass().getName(), value));
        }

        if (value.contains(".")) {
            try {
                this.value = new Double(value);
            } catch (NumberFormatException err) {
                this.value = new BigDecimal(value);
            }
        } else {
            try {
                this.value = new Integer(value);
            } catch (NumberFormatException err) {
                this.value = new BigInteger(value);
            }
        }
    }

    AtomNumber(Number value) {
        this.value = value;
    }

    @Override
    public Number getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).toPlainString();
        } else {
            return value.toString();
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof AtomNumber) {
            Number buf = ((AtomNumber) object).value;
            if (value instanceof Integer && buf instanceof Integer) {
                return (Integer) value == buf.intValue();
            } else {
                BigDecimal vb = (value instanceof BigDecimal) ? (BigDecimal) value : new BigDecimal(value.toString());
                BigDecimal bb = (buf instanceof BigDecimal) ? (BigDecimal) buf : new BigDecimal(buf.toString());
                return vb.equals(bb);
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }

    /**
     * AtomNumber表現かを検査する
     *
     * @param value 検査したいString
     * @return AtomNumberであればTrue
     */
    public static boolean isAtomNumber(String value) {
        Matcher matcher = pattern_number.matcher(value);
        return matcher.matches();
    }
}
