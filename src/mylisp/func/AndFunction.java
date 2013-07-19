/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.AtomBoolean;
import mylisp.core.AtomSymbol;
import mylisp.core.Cell;
import mylisp.core.Sexp;

/**
 * and class
 * @author moremagic
 */
public class AndFunction implements IFunction{

    @Override
    public Sexp eval(Cell cell, Map<AtomSymbol, Sexp> env) throws FunctionException {
        for(Sexp sexp: cell.getCdr()){
            Sexp sexp_apply = MyLisp.apply(sexp, env);
            if(sexp_apply.toString().equals(AtomBoolean.F)){
                return AtomBoolean.AtomFalse;
            }
        }
        return AtomBoolean.AtomTrue;
    }
    
    @Override
    public String functionSymbol() {
        return "and";
    }

}
