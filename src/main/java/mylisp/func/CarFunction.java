package mylisp.func;

import mylisp.MyLisp;
import mylisp.core.*;

import java.util.Map;

/**
 * car class
 *
 * @author moremagic
 */
public class CarFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
        super.checkArgument(cons, 1);

        Sexp cdr = MyLisp.apply(cons.getCdr(), env);
        if (cdr instanceof IPair) {
            return ((IPair) cdr).getCar();
        } else {
            throw new FunctionException(operatorSymbol() + ": expects argument of type <pair>; given " + cdr);
        }
    }

    @Override
    public String operatorSymbol() {
        return "car";
    }
}
