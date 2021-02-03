package mylisp.func;

import mylisp.MyLisp;
import mylisp.core.*;

import java.util.Map;

/**
 * time class
 *
 * @author moremagic
 */
public class TimeFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
        long start_time = System.currentTimeMillis();
        Sexp ret = MyLisp.eval(cons.getCdr(), env);
        System.out.println((System.currentTimeMillis() - start_time) + "[ms]");
        return ret;
    }

    @Override
    public String operatorSymbol() {
        return "time";
    }

}
