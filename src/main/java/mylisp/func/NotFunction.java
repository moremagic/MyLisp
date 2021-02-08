package mylisp.func;

import mylisp.MyLisp;
import mylisp.core.*;

import java.util.Map;

/**
 * not class
 *
 * @author moremagic
 */
public class NotFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cell, Map<AtomSymbol, Sexp> env) throws MyLispException {
        super.checkArgument(cell, 1);

        Sexp sexp = MyLisp.apply(cell.getCdr(), env);
        return Atom.newAtom(sexp == AtomBoolean.AtomFalse);
    }

    @Override
    public String operatorSymbol() {
        return "not";
    }
}
