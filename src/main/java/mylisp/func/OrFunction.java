package mylisp.func;

import mylisp.MyLisp;
import mylisp.core.*;

import java.util.Map;

/**
 * or class
 *
 * @author moremagic
 */
public class OrFunction implements SpecialOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
        for (Sexp sexp : cons.getCdr().getList()) {
            Sexp sexp_apply = MyLisp.apply(sexp, env);
            if (sexp_apply == AtomBoolean.AtomTrue) {
                return Atom.newAtom(true);
            }
        }
        return Atom.newAtom(false);
    }

    @Override
    public String operatorSymbol() {
        return "or";
    }
}
