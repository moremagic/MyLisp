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
    public String operatorSymbol();
    public Sexp eval(Cell cell, Map<AtomSymbol, Sexp> env) throws FunctionException;
}
