/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.Atom;
import mylisp.core.AtomNumber;
import mylisp.core.AtomSymbol;
import mylisp.core.Cell;
import mylisp.core.Sexp;

/**
 * +(Plus) Function
 *
 * @author moremagic
 */
public class AddFunction implements IFunction {

    @Override
    public Sexp eval(Cell cell, Map<AtomSymbol, Sexp> env) throws FunctionException {
        Number ret = 0;
        for (Sexp s : cell.getCdr()) {
            Sexp buf = MyLisp.apply(s, env);

            if (buf instanceof AtomNumber) {
                ret = addNumber(ret, ((AtomNumber) buf).getValue());
            } else {
                throw new FunctionException("reference to undefined identifier: " + buf.toString());
            }
        }

        return Atom.newAtom(ret);
    }

    private Number addNumber(Number a, Number b) {
        Number ret;
        if (a instanceof Integer && b instanceof Integer) {
            ret = new BigInteger(((Integer) a).toString()).add(new BigInteger(((Integer) b).toString()));
        } else {
            BigDecimal ab = (a instanceof BigDecimal) ? (BigDecimal) a : new BigDecimal((double) a.doubleValue());
            BigDecimal bb = (b instanceof BigDecimal) ? (BigDecimal) b : new BigDecimal((double) b.doubleValue());
            ret = ab.add(bb);
        }

        return ret;
    }

    @Override
    public String functionSymbol() {
        return "+";
    }
}
