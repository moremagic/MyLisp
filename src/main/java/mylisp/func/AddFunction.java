package mylisp.func;

import mylisp.MyLisp;
import mylisp.core.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

/**
 * +(Plus) Function
 *
 * @author moremagic
 */
public class AddFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
        Sexp ret = Atom.newAtom(0);
        for (Sexp s : ((IPair) cons.getCdr()).getList()) {
            Sexp buf = MyLisp.eval(s, env);
            if (buf instanceof AtomNumber) {
                ret = Atom.newAtom(addNumber(((AtomNumber) ret).getValue(), ((AtomNumber) buf).getValue()));
            } else {
                throw new FunctionException("reference to undefined identifier: " + buf.toString());
            }
        }

        return ret;
    }

    private Number addNumber(Number a, Number b) {
        Number ret;
        if (a instanceof Integer && b instanceof Integer) {
            ret = new BigInteger(((Integer) a).toString()).add(new BigInteger(((Integer) b).toString()));
        } else {
            BigDecimal ab = (a instanceof BigDecimal) ? (BigDecimal) a : new BigDecimal(a.toString());
            BigDecimal bb = (b instanceof BigDecimal) ? (BigDecimal) b : new BigDecimal(b.toString());
            ret = ab.add(bb);
        }

        return ret;
    }

    @Override
    public String operatorSymbol() {
        return "+";
    }
}
