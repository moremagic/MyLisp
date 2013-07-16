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
 * eq?(Equal) Function
 *
 * @author moremagic
 */
public class EqualFunction implements IFunction {

    @Override
    public Sexp eval(Cell cell, Map<String, Sexp> env) throws FunctionException {
        if(cell.getCdr().length != 2){
            throw new FunctionException("eq?: expects 2 arguments, given " + cell.getCdr().length);
        }
        
        Sexp sexpA = MyLisp.apply(cell.getCdr()[0], env);
        Sexp sexpB = MyLisp.apply(cell.getCdr()[1], env);
        return Atom.newAtom(sexpA.toString().equals(sexpB.toString()));
    }

    @Override
    public String functionSymbol() {
        return "eq?";
    }
}
