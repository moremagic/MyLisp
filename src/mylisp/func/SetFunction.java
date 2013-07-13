/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.core.Atom;
import mylisp.core.Cell;
import mylisp.core.Sexp;

/**
 * set! class
 * @author moremagic
 */
public class SetFunction implements IFunction{

    @Override
    public Sexp eval(Cell cell, Map<String, Sexp> env) throws FunctionException {
        if(cell.getSexps().length != 3){
            throw new FunctionException("set!: bad syntax in: " + cell.toString());
        }
        
        Sexp[] cdrs = cell.getCdr();
        if(env.containsKey(cdrs[0].toString())){
            env.put(cdrs[0].toString(), cdrs[1]);
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
