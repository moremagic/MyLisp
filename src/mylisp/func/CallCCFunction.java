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
 * call/cc class
 * ※実装途中です
 * 
 * @author moremagic
 */
public class CallCCFunction implements IFunction{

    @Override
    public Sexp eval(Cell cell, Map<AtomSymbol, Sexp> env) throws FunctionException {
        if(cell.getCdr().length != 1){
            throw new FunctionException(functionSymbol() + ": expects 1 argument, given " + cell.getCdr().length );
        }
        
        Sexp sexp = MyLisp.eval(cell.getCdr()[cell.getCdr().length-1], env);
        System.out.println("------------ call/cc eval! ----------");
        return Atom.newAtom(1);
    }
    
    @Override
    public String functionSymbol() {
        return "call/cc";
    }

}
