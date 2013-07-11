/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.Atom;
import mylisp.core.Cell;
import mylisp.core.Sexp;

/**
 * define Function
 * @author moremagic
 */
public class DefineFunction implements IFunction{
    @Override
    public Sexp eval(Cell cell, Map<String, Sexp> env) throws FunctionException{        
        Sexp[] cdrs = cell.getCdr();
        if(cdrs.length == 2){
            if(cdrs[0] instanceof Cell){
                Sexp car = ((Cell)cdrs[0]).getCar();
                Sexp[] cdr = ((Cell)cdrs[0]).getCdr();
                                
                env.put(car.toString(), new Cell(Atom.newAtom("lambda"), new Sexp[]{new Cell(cdr), cdrs[1]}));
            }else{
                env.put(cdrs[0].toString(), MyLisp.apply(cdrs[1], env));
            }
        }else{
            throw new FunctionException("define: expects arguments");
        }
        
        return env.get(cdrs[0].toString());
    }

    @Override
    public String functionSymbol() {
        return "define";
    }
}
