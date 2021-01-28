/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.AbstractOperator;
import mylisp.core.Atom;
import mylisp.core.AtomSymbol;
import mylisp.core.IPair;
import mylisp.core.Sexp;

/**
 * eq?(Equal) Function
 *
 * @author moremagic
 */
public class EqualFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        super.checkArgument(cons, 2);

        Sexp[] list = cons.getCdr().getList();
        Sexp sexpA = MyLisp.apply(list[0], env);
        Sexp sexpB = MyLisp.apply(list[1], env);
//        return Atom.newAtom(sexpA == sexpB);
        return Atom.newAtom(sexpA.equals(sexpB));
    }

    @Override
    public String operatorSymbol() {
        return "eq?";
    }
}
