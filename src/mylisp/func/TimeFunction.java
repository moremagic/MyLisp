/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.AtomSymbol;
import mylisp.core.Cell;
import mylisp.core.Sexp;

/**
 * time class
 * @author moremagic
 */
public class TimeFunction implements IFunction{

    @Override
    public Sexp eval(Cell cell, Map<AtomSymbol, Sexp> env) throws FunctionException {
        long start_time = System.currentTimeMillis();
        Sexp ret = MyLisp.eval(cell.getCdr()[0], env);
        System.out.println((System.currentTimeMillis() - start_time) + "[ms]");
        return ret;
    }
    
    @Override
    public String functionSymbol() {
        return "time";
    }

}
