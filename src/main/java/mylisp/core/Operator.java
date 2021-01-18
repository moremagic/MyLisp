/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.core;

import java.util.Map;
import mylisp.func.FunctionException;


/**
 * Operator interface
 * @author moremagic
 */
public interface Operator {
    abstract public String operatorSymbol();
    abstract public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException;
}
