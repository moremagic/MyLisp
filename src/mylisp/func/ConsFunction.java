/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.core.Cell;
import mylisp.core.Sexp;

/**
 * cons Function
 * @author moremagic
 */
public class ConsFunction implements IFunction{
    @Override
    public Sexp eval(Cell cell, Map<String, Sexp> env) throws FunctionException{
        if(cell.getCdr().length > 2){
            throw new FunctionException("cons: expects 2 arguments");
        }
        
        Sexp[] cdr;
        if(cell.getCdr()[1] instanceof Cell){
            cdr = ((Cell)cell.getCdr()[1]).getSexps();
        }else{
            cdr = new Sexp[]{cell.getCdr()[1]};
        }
        
        return new Cell(cell.getCdr()[0], cdr);
    }
}
