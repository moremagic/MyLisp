package mylisp.func;

import mylisp.MyLisp;
import mylisp.core.*;

import java.util.Map;

/**
 * and class
 *
 * @author moremagic
 */
public class AndFunction implements SpecialOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
        for (Sexp sexp : ((IPair) cons.getCdr()).getList()) {
            Sexp sexp_apply = MyLisp.apply(sexp, env);
            if (sexp_apply == AtomBoolean.AtomFalse) {
                return AtomBoolean.AtomFalse;
            }
        }
        return AtomBoolean.AtomTrue;
    }

    @Override
    public String operatorSymbol() {
        return "and";
    }
}
