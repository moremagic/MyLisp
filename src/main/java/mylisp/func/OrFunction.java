/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.Atom;
import mylisp.core.AtomBoolean;
import mylisp.core.AtomSymbol;
import mylisp.core.IPair;
import mylisp.core.Sexp;
import mylisp.core.SpecialOperator;

/**
 * or class
 *
 * @author moremagic
 */
public class OrFunction implements SpecialOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
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