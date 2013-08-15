/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.core.AtomSymbol;
import mylisp.core.IPair;
import mylisp.core.Sexp;
import mylisp.core.SpecialOperator;

/**
 * quote Function
 *
 * @author moremagic
 */
public class QuoteFunction implements SpecialOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        return cons.getCdr();
    }

    @Override
    public String operatorSymbol() {
        return "quote";
    }
}
