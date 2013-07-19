/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.Atom;
import mylisp.core.AtomSymbol;
import mylisp.core.Cell;
import mylisp.core.Sexp;

/**
 * set! class
 * @author moremagic
 */
public class SetFunction implements IFunction{

    @Override
    public Sexp eval(Cell cell, Map<AtomSymbol, Sexp> env) throws FunctionException {
        if(cell.getSexps().length != 3){
            throw new FunctionException("set!: bad syntax in: " + cell.toString());
        }
        
        Sexp[] cdrs = cell.getCdr();
        if(env.containsKey((AtomSymbol)cdrs[0])){
            env.put((AtomSymbol) cdrs[0], MyLisp.apply(cdrs[1], env));
        }else{
            throw new FunctionException("set!: cannot set undefined identifier: " + cdrs[0].toString());
        }
        return Atom.newAtom("()");
    }
    
    @Override
    public String functionSymbol() {
        return "set!";
    }
}
