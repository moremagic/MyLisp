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
 * eof-object? class
 *
 * @author moremagic
 */
public class IsEOF extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        super.checkArgument(cons, 1);

        Sexp sexp = MyLisp.apply(cons.getCdr().getList()[0], env);
        return Atom.newAtom(sexp.toString().equals(new String(new char[]{'\uffff'})));
    }

    @Override
    public String operatorSymbol() {
        return "eof-object?";
    }
}
