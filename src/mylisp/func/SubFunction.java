/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.math.BigDecimal;
import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.Atom;
import mylisp.core.AtomNumber;
import mylisp.core.AtomSymbol;
import mylisp.core.Cell;
import mylisp.core.Sexp;

/**
 * -(subtract) Function
 *
 * @author moremagic
 */
public class SubFunction implements IFunction {

    @Override
    public Sexp eval(Cell cell, Map<AtomSymbol, Sexp> env) throws FunctionException {
        Number ret = null;
        for (Sexp s : cell.getCdr()) {
            Sexp buf = MyLisp.apply(s, env);

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
    public String functionSymbol() {
        return "-";
    }
}
