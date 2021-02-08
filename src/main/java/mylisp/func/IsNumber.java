package mylisp.func;

import mylisp.MyLisp;
import mylisp.core.*;

import java.util.Map;

/**
 * number? class
 *
 * @author moremagic
 */
public class IsNumber extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
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
