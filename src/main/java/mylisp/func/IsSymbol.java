package mylisp.func;

import mylisp.MyLisp;
import mylisp.core.*;

import java.util.Map;

/**
 * symbol? class
 *
 * @author moremagic
 */
public class IsSymbol extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
        super.checkArgument(cons, 1);

        Sexp sexp = MyLisp.apply(cons.getCdr(), env);
        return Atom.newAtom(sexp instanceof AtomSymbol);
    }

    @Override
    public String operatorSymbol() {
        return "symbol?";
    }
}
