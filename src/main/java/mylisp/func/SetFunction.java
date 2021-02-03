package mylisp.func;

import mylisp.MyLisp;
import mylisp.core.*;

import java.util.Map;

/**
 * set! class
 *
 * @author moremagic
 */
public class SetFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
        super.checkArgument(cons, 2);

        Sexp[] cdrs = cons.getCdr().getList();
        if (!env.containsKey(cdrs[0])) {
            throw new FunctionException("set!: cannot set undefined identifier: " + cdrs[0].toString());
        }

        Sexp ret = MyLisp.apply(cdrs[1], env);
        env.put((AtomSymbol) cdrs[0], ret);
        return ret;
    }

    @Override
    public String operatorSymbol() {
        return "set!";
    }
}
