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
 * > (greaterThan) class
 *
 * @author moremagic
 */
public class GThanFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        super.checkArgmunet(cons, 2);

        Sexp[] list = cons.getCdr().getList();
        Sexp sexp_apply1 = MyLisp.apply(list[0], env);
        Sexp sexp_apply2 = MyLisp.apply(list[1], env);
        if (sexp_apply1 instanceof AtomNumber && sexp_apply2 instanceof AtomNumber) {
            return Atom.newAtom(greaterThanNumber(((AtomNumber) sexp_apply1).getValue(), ((AtomNumber) sexp_apply2).getValue()));
        } else {
            throw new FunctionException("reference to undefined identifier: " + cons.toString());
        }
    }

    private boolean greaterThanNumber(Number a, Number b) {
        boolean ret;
        if (a instanceof Double && b instanceof Double) {
            ret = (Double) a > (Double) b;
        } else if (a instanceof Integer && b instanceof Integer) {
            ret = (Integer) a > (Integer) b;
        } else {
            ret = (new BigDecimal((double) a.doubleValue()).compareTo(new BigDecimal((double) b.doubleValue())) > 0);
        }

        return ret;
    }

    @Override
    public String operatorSymbol() {
        return ">";
    }
}
