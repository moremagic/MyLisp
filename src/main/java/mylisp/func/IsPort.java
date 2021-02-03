package mylisp.func;

import java.util.Map;

import mylisp.MyLisp;
import mylisp.core.*;

/**
 * port? class
 *
 * @author moremagic
 */
public class IsPort extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
        super.checkArgument(cons, 1);

        Sexp sexp = MyLisp.apply(cons.getCdr(), env);
        if (sexp instanceof AtomSymbol) {
            throw new FunctionException("reference to undefined identifier: " + sexp);
        }
        return Atom.newAtom(sexp instanceof AtomPort);
    }

    @Override
    public String operatorSymbol() {
        return "port?";
    }
}
