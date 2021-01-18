/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.AtomBoolean;
import mylisp.core.AtomSymbol;
import mylisp.core.IPair;
import mylisp.core.Sexp;
import mylisp.core.SpecialOperator;

/**
 * and class
 *
 * @author moremagic
 */
public class AndFunction implements SpecialOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
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
