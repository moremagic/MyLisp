/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import mylisp.core.Operator;
import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.Atom;
import mylisp.core.AtomSymbol;
import mylisp.core.Cell;
import mylisp.core.IPair;
import mylisp.core.Sexp;

/**
 * pair? class
 * @author moremagic
 */
public class IsPair implements Operator{

    @Override
    public Sexp eval(Cell cell, Map<AtomSymbol, Sexp> env) throws FunctionException {
        if(cell.getCdr().length != 1){
            throw new FunctionException(operatorSymbol() + ": expects 1 argument, given " + cell.getCdr().length );
        }
        
        Sexp sexp = MyLisp.apply(cell.getCdr()[0], env);
        return Atom.newAtom(sexp instanceof IPair);
    }
    
    @Override
    public String operatorSymbol() {
        return "pair?";
    }

}
