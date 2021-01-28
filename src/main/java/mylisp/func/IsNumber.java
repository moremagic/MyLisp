/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.AbstractOperator;
import mylisp.core.Atom;
import mylisp.core.AtomNumber;
import mylisp.core.AtomSymbol;
import mylisp.core.IPair;
import mylisp.core.Sexp;

/**
 * number? class
 *
 * @author moremagic
 */
public class IsNumber extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        super.checkArgument(cons, 1);

        Sexp sexp = MyLisp.apply(cons.getCdr(), env);

        //TODO: sexp がConsCellになってるので常にFalseになるバグを見つけた
        return Atom.newAtom(sexp instanceof AtomNumber);
    }

    @Override
    public String operatorSymbol() {
        return "number?";
    }
}
