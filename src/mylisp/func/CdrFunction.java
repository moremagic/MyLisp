/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.Cell;
import mylisp.core.IPair;
import mylisp.core.Sexp;

/**
 * cdr class
 *
 * @author moremagic
 */
public class CdrFunction implements IFunction {

    @Override
    public Sexp eval(Cell cell, Map<String, Sexp> env) throws FunctionException {
        if (cell.getCdr().length != 1) {
            throw new FunctionException("cdr: expects 1 argument, given " + cell.getCdr().length);
        }

        Sexp cdr = MyLisp.apply(cell.getCdr()[0], env);
        if(cdr instanceof IPair){
            return new Cell(((IPair)cdr).getCdr());
        }else{
            throw new FunctionException("cdr: expects argument of type <pair>; given " + cdr);
        }
    }

    @Override
    public String functionSymbol() {
        return "cdr";
    }
}
