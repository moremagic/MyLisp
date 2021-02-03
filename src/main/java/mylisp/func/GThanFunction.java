package mylisp.func;

import mylisp.MyLisp;
import mylisp.core.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * > (greaterThan) class
 *
 * @author moremagic
 */
public class GThanFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
        super.checkArgument(cons, 2);

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
            ret = (new BigDecimal(a.toString()).compareTo(new BigDecimal(b.toString())) > 0);
        }

        return ret;
    }

    @Override
    public String operatorSymbol() {
        return ">";
    }
}
