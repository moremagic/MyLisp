package mylisp.func;

import java.util.Map;

import mylisp.MyLisp;
import mylisp.core.*;

/**
 * Cond class
 *
 * @author moremagic
 */
public class CondFunction implements SpecialOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        Sexp ret = AtomNil.INSTANCE;

        Sexp[] buf = cons.getCdr().getList();
        for (int i = 0; i < buf.length; i++) {
            if (buf[i] instanceof IPair) {
                IPair cdr = (IPair) buf[i];
                Sexp cadr = MyLisp.eval(cdr.getCar(), env);
                Sexp cddr = cdr.getCdr();

                if (cadr == AtomBoolean.AtomTrue || (i == buf.length - 1 && cadr.toString().equals("else"))) {
                    ret = MyLisp.evals(cddr, env);
                    break;
                }
            } else {
                throw new FunctionException("cond: bad syntax (clause is not a test-value pair) in: " + buf[i].toString());
            }
        }
        return ret;
    }

    @Override
    public String operatorSymbol() {
        return "cond";
    }
}
