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
import mylisp.core.Cell;
import mylisp.core.Sexp;

/**
 * > (greaterThan) class
 *
 * @author moremagic
 */
public class GThanFunction implements IFunction {

    @Override
    public Sexp eval(Cell cell, Map<String, Sexp> env) throws FunctionException {
        if (cell.getCdr().length != 2) {
            throw new FunctionException(">: expects at least 2 arguments, given " + cell.getCdr().length);
        }

        Sexp sexp_apply1 = MyLisp.apply(cell.getCdr()[0], env);
        Sexp sexp_apply2 = MyLisp.apply(cell.getCdr()[1], env);
        if (sexp_apply1 instanceof AtomNumber && sexp_apply2 instanceof AtomNumber) {
            return Atom.newAtom((greaterThanNumber(((AtomNumber) sexp_apply1).getValue(), ((AtomNumber) sexp_apply2).getValue())) ? "#t" : "#f");
        } else {
            throw new FunctionException("reference to undefined identifier:");
        }
    }

    private boolean greaterThanNumber(Number a, Number b) {
        boolean ret;
        if (a instanceof Double && b instanceof Double) {
            ret = (Double) a > (Double) b;
        } else if (a instanceof Integer && b instanceof Integer) {
            ret = (Integer) a > (Integer) b;
        } else {
            ret = (new BigDecimal((double)a.doubleValue()).compareTo(new BigDecimal((double)b.doubleValue())) > 0);
        }

        return ret;
    }

    @Override
    public String functionSymbol() {
        return ">";
    }
}