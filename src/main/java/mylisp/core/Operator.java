package mylisp.core;

import java.util.Map;


/**
 * Operator interface
 * TODO スペシャルフォームの実行メソッドはapply　であるべき
 *
 * @author moremagic
 */
public interface Operator {
    abstract public String operatorSymbol();

    abstract public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException;
}
