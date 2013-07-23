/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import mylisp.core.Operator;
import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.Atom;
import mylisp.core.AtomPort;
import mylisp.core.AtomSymbol;
import mylisp.core.Cell;
import mylisp.core.Sexp;

/**
 * port? class
 * @author moremagic
 */
public class IsPort implements Operator{

    @Override
    public Sexp eval(Cell cell, Map<AtomSymbol, Sexp> env) throws FunctionException {
        if(cell.getCdr().length != 1){
            throw new FunctionException(operatorSymbol() + ": expects 1 argument, given " + cell.getCdr().length );
        }
        
        Sexp sexp = MyLisp.apply(cell.getCdr()[0], env);
        if(sexp instanceof AtomSymbol){
            throw new FunctionException("reference to undefined identifier: " + sexp);
        }
        return Atom.newAtom(sexp instanceof AtomPort);
    }
    
    @Override
    public String operatorSymbol() {
        return "port?";
    }

}
