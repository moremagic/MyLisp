package mylisp.func;

import mylisp.MyLisp;
import mylisp.core.*;

import java.util.Map;

/**
 * boolean? class
 *
 * @author moremagic
 */
public class IsBoolean extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
        super.checkArgument(cons, 1);

        Sexp sexp = MyLisp.apply(cons.getCdr(), env);
        if (sexp instanceof AtomSymbol) {
            throw new FunctionException("reference to undefined identifier: " + sexp);
        }
        return Atom.newAtom(sexp instanceof AtomBoolean);
    }

    @Override
    public String operatorSymbol() {
        return "boolean?";
    }
}
