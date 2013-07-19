/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.core.AtomSymbol;
import mylisp.core.Cell;
import mylisp.core.Sexp;

/**
 * quote Function
 * @author moremagic
 */
public class QuoteFunction implements IFunction{
    @Override
    public Sexp eval(Cell cell, Map<AtomSymbol, Sexp> env) throws FunctionException{
        return cell.getCdr()[0];
    }
    
    @Override
    public String functionSymbol() {
        return "quote";
    }

}
