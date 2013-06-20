/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.core.Cell;
import mylisp.core.Sexp;

/**
 * '(quote) Function
 * @author mitsu
 */
public class QuoteFunction implements IFunction{
    @Override
    public Sexp eval(Cell cell, Map<String, Sexp> env) throws FunctionException{
        return cell.getCdr()[0];
    }
}
