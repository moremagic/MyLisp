/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.AbstractOperator;
import mylisp.core.Atom;
import mylisp.core.AtomString;
import mylisp.core.AtomSymbol;
import mylisp.core.IPair;
import mylisp.core.Sexp;

/**
 * string? class
 *
 * @author moremagic
 */
public class IsString extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        super.checkArgmunet(cons, 1);

        Sexp sexp = MyLisp.apply(cons.getCdr(), env);
        if (sexp instanceof AtomSymbol) {
            throw new FunctionException("reference to undefined identifier: " + sexp);
        }
        return Atom.newAtom(sexp instanceof AtomString);
    }

    @Override
    public String operatorSymbol() {
        return "string?";
    }
}
