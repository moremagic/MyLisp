package mylisp.func;

import mylisp.MyLisp;
import mylisp.core.*;

import java.util.Map;

/**
 * cdr class
 *
 * @author moremagic
 */
public class CdrFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
        super.checkArgument(cons, 1);

        Sexp cdr = MyLisp.apply(cons.getCdr(), env);
        if (cdr instanceof IPair) {
            return ((IPair) cdr).getCdr();
        } else {
            throw new FunctionException("cdr: expects argument of type <pair>; given " + cdr);
        }
    }

    @Override
    public String operatorSymbol() {
        return "cdr";
    }
}
