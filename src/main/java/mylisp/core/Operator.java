package mylisp.core;

import java.util.Map;


/**
 * Operator interface
 *
 * @author moremagic
 */
public interface Operator {
    abstract public String operatorSymbol();

    abstract public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException;
}
