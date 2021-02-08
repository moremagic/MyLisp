package mylisp.func;

import mylisp.MyLisp;
import mylisp.core.*;

import java.util.Map;

/**
 * null? class
 *
 * @author moremagic
 */
public class IsNull extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
        super.checkArgument(cons, 1);

        Sexp sexp = MyLisp.apply(cons.getCdr(), env);
        return Atom.newAtom(sexp == AtomNil.INSTANCE || (sexp instanceof IPair && ((IPair) sexp).getCar() == AtomNil.INSTANCE));
    }

    @Override
    public String operatorSymbol() {
        return "null?";
    }
}
