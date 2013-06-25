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
 * pair? class
 * @author moremagic
 */
public class PairFunction implements IFunction{

    @Override
    public Sexp eval(Cell cell, Map<String, Sexp> env) throws FunctionException {
        if(cell.getCdr().length != 1){
            throw new FunctionException("pair?: expects 1 argument, given " + cell.getCdr().length );
        }
        
        Sexp sexp = MyLisp.apply(cell.getCdr()[0], env);
        return (sexp instanceof Cell)?Atom.newAtom("#t"):Atom.newAtom("#f");
    }
    
}
