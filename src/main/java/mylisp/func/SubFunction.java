package mylisp.func;

import mylisp.MyLisp;
import mylisp.core.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * -(subtract) Function
 *
 * @author moremagic
 */
public class SubFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
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
            ret = BigDecimal.valueOf(a.doubleValue()).subtract(BigDecimal.valueOf(b.doubleValue()));
        }

        return ret;
    }

    @Override
    public String operatorSymbol() {
        return "-";
    }
}
