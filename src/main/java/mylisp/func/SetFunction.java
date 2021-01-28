package mylisp.func;

import java.util.Map;

import mylisp.MyLisp;
import mylisp.core.*;

/**
 * set! class
 *
 * @author moremagic
 */
public class SetFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        super.checkArgument(cons, 2);

        Sexp ret = AtomNil.INSTANCE;
        Sexp[] cdrs = cons.getCdr().getList();
        if (env.containsKey((AtomSymbol) cdrs[0])) {
            ret = MyLisp.apply(cdrs[1], env);
            env.put((AtomSymbol) cdrs[0], ret);
        } else {
            throw new FunctionException("set!: cannot set undefined identifier: " + cdrs[0].toString());
        }
        return ret;
    }

    @Override
    public String operatorSymbol() {
        return "set!";
    }
}
