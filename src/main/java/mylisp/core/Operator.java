package mylisp.core;

import java.util.Map;


/**
 * Operator interface
 *
 * @author moremagic
 */
public interface Operator {
    abstract public String operatorSymbol();

    abstract public Sexp apply(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException;
}
