/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import mylisp.core.Operator;
import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.AtomSymbol;
import mylisp.core.Cell;
import mylisp.core.IPair;
import mylisp.core.Sexp;

/**
 * car class
 *
 * @author moremagic
 */
public class CarFunction implements Operator {

    @Override
    public Sexp eval(Cell cell, Map<AtomSymbol, Sexp> env) throws FunctionException {
        if (cell.getCdr().length != 1) {
            throw new FunctionException(operatorSymbol() + ": expects 1 argument, given " + cell.getCdr().length);
        }

        Sexp cdr = MyLisp.apply(cell.getCdr()[0], env);
        if(cdr instanceof IPair){
            return ((IPair)cdr).getCar();
        }else{
            throw new FunctionException(operatorSymbol() + ": expects argument of type <pair>; given " + cdr);
        }
    }

    @Override
    public String operatorSymbol() {
        return "car";
    }
}
