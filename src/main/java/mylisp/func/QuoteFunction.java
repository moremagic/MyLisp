package mylisp.func;

import mylisp.core.*;

import java.util.Map;

/**
 * quote Function
 *
 * @author moremagic
 */
public class QuoteFunction implements SpecialOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
        Sexp ret = cons.getCdr();
        return (ret instanceof IPair) ? ((IPair) ret).getCar() : ret;
    }

    @Override
    public String operatorSymbol() {
        return "quote";
    }
}
