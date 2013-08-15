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
import mylisp.core.ConsCell;
import mylisp.core.IPair;
import mylisp.core.Sexp;

/**
 * null? class
 *
 * @author moremagic
 */
public class IsNull extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        super.checkArgmunet(cons, 1);

        Sexp sexp = MyLisp.apply(cons.getCdr(), env);
        return Atom.newAtom(sexp instanceof IPair && (((IPair)sexp).getCar() == null || ((IPair)sexp).getCar() == ConsCell.NIL));
    }

    @Override
    public String operatorSymbol() {
        return "null?";
    }
}
