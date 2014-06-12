/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.AbstractOperator;
import mylisp.core.Atom;
import mylisp.core.AtomBoolean;
import mylisp.core.AtomSymbol;
import mylisp.core.IPair;
import mylisp.core.Sexp;

/**
 * not class
 *
 * @author moremagic
 */
public class NotFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cell, Map<AtomSymbol, Sexp> env) throws FunctionException {
        super.checkArgmunet(cell, 1);

        Sexp sexp = MyLisp.apply(cell.getCdr(), env);
        return Atom.newAtom(sexp == AtomBoolean.AtomFalse);
    }

    @Override
    public String operatorSymbol() {
        return "not";
    }
}
