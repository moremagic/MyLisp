/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.math.BigDecimal;
import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.AbstractOperator;
import mylisp.core.Atom;
import mylisp.core.AtomNumber;
import mylisp.core.AtomSymbol;
import mylisp.core.IPair;
import mylisp.core.Sexp;

/**
 * -(subtract) Function
 *
 * @author moremagic
 */
public class SubFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        Number ret = null;
        for (Sexp s : cons.getCdr().getList()) {
            Sexp buf = MyLisp.eval(s, env);

            if (buf instanceof AtomNumber) {
                if (ret == null) {
                    ret = ((AtomNumber) buf).getValue();
                } else {
                    ret = subNumber(ret, ((AtomNumber) buf).getValue());
                }
            } else {
                throw new FunctionException("reference to undefined identifier: " + buf.toString());
            }
        }

        return Atom.newAtom(ret);
    }

    private Number subNumber(Number a, Number b) {
        Number ret;
        if (a instanceof Double && b instanceof Double) {
            ret = (Double) a - (Double) b;
        } else if (a instanceof Integer && b instanceof Integer) {
            ret = (Integer) a - (Integer) b;
        } else {
            ret = new BigDecimal((double) a.doubleValue()).subtract(new BigDecimal((double) b.doubleValue()));
        }

        return ret;
    }

    @Override
    public String operatorSymbol() {
        return "-";
    }
}
