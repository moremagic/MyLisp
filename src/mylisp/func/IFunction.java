/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.core.Cell;
import mylisp.core.Sexp;


/**
 * Function interface
 * @author moremagic
 */
public interface IFunction {
    public Sexp eval(Cell cell, Map<String, Sexp> env) throws FunctionException;
}
