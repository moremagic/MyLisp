package mylisp.func;

import mylisp.MyLisp;
import mylisp.core.*;

import java.util.Map;

/**
 * eq?(Equal) Function
 *
 * @author moremagic
 */
public class EqualFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
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
