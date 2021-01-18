/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.AbstractOperator;
import mylisp.core.Atom;
import mylisp.core.AtomNumber;
import mylisp.core.AtomSymbol;
import mylisp.core.IPair;
import mylisp.core.Sexp;

/**
 * *(multiply) Function
 *
 * @author moremagic
 */
public class MultiFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
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
            ret = new BigInteger(((Integer) a).toString()).multiply(new BigInteger(((Integer) b).toString()));
        } else {
            BigDecimal ab = (a instanceof BigDecimal) ? (BigDecimal) a : new BigDecimal((double) a.doubleValue());
            BigDecimal bb = (b instanceof BigDecimal) ? (BigDecimal) b : new BigDecimal((double) b.doubleValue());
            ret = ab.multiply(bb);
        }

        return ret;
    }

    @Override
    public String operatorSymbol() {
        return "*";
    }
}