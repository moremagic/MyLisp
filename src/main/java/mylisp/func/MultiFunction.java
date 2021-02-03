package mylisp.func;

import mylisp.MyLisp;
import mylisp.core.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

/**
 * *(multiply) Function
 *
 * @author moremagic
 */
public class MultiFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
        Number ret = null;
        for (Sexp s : cons.getCdr().getList()) {
            Sexp buf = MyLisp.apply(s, env);

            if (buf instanceof AtomNumber) {
                if (ret == null) {
                    ret = ((AtomNumber) buf).getValue();
                } else {
                    ret = multiNumber(ret, ((AtomNumber) buf).getValue());
                }
            } else {
                throw new FunctionException("reference to undefined identifier: " + buf.toString());
            }
        }

        return Atom.newAtom(ret);
    }

    private Number multiNumber(Number a, Number b) {
        Number ret;
        if (a instanceof Integer && b instanceof Integer) {
            ret = BigInteger.valueOf(Long.parseLong(a.toString())).multiply(BigInteger.valueOf(Long.parseLong(b.toString())));
        } else {
            BigDecimal ab = (a instanceof BigDecimal) ? (BigDecimal) a : BigDecimal.valueOf(a.doubleValue());
            BigDecimal bb = (b instanceof BigDecimal) ? (BigDecimal) b : BigDecimal.valueOf(b.doubleValue());
            ret = ab.multiply(bb);
        }

        return ret;
    }

    @Override
    public String operatorSymbol() {
        return "*";
    }
}
