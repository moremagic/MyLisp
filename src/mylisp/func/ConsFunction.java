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
 * cons Function
 * @author moremagic
 */
public class ConsFunction implements Operator{
    @Override
    public Sexp eval(Cell cell, Map<AtomSymbol, Sexp> env) throws FunctionException{
        if(cell.getCdr().length > 2){
            throw new FunctionException("cons: expects 2 arguments");
        }
        
        Sexp cdr = MyLisp.apply(cell.getCdr()[1], env);
        if(cdr instanceof IPair){            
            return ((IPair)cdr).cons(MyLisp.apply(cell.getCdr()[0], env));
        }else{
            return new Cell(cdr, MyLisp.apply(cell.getCdr()[0], env));
        }
    }
    
    
    @Override
    public String operatorSymbol() {
        return "cons";
    }

}
