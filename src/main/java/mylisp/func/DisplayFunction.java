package mylisp.func;

import mylisp.MyLisp;
import mylisp.core.*;

import java.util.Map;

/**
 * display class
 *
 * @author moremagic
 */
public class DisplayFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
        super.checkArgument(cons, 1);

        Sexp ret = MyLisp.apply(cons.getCdr().getList()[0], env);
        System.out.print(ret.toString());
        return ret;
    }

    @Override
    public String operatorSymbol() {
        return "display";
    }
}
