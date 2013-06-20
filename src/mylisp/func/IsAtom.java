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
 * atom? class
 * @author mitsu
 */
public class IsAtom implements IFunction{

    @Override
    public Sexp eval(Cell cell, Map<String, Sexp> env) throws FunctionException {
        if(cell.getCdr().length != 1){
            throw new FunctionException("atom?: expects " + cell.getCdr().length + " argument");
        }
        return Atom.newAtom((cell.getCdr()[0] instanceof Atom)?"#t":"#f");
    }
    
}
