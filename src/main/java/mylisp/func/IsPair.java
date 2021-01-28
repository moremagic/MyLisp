package mylisp.func;

import java.util.Map;

import mylisp.MyLisp;
import mylisp.core.*;

/**
 * pair? class
 *
 * @author moremagic
 */
public class IsPair extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        super.checkArgument(cons, 1);

        Sexp sexp = MyLisp.apply(cons.getCdr(), env);
        return Atom.newAtom((sexp instanceof IPair) && ((IPair) sexp).getCar() != AtomNil.INSTANCE);
    }

    @Override
    public String operatorSymbol() {
        return "pair?";
    }
}
