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
 * null? class
 * @author moremagic
 */
public class IsNull implements IFunction{

    @Override
    public Sexp eval(Cell cell, Map<String, Sexp> env) throws FunctionException {
        if(cell.getCdr().length != 1){
            throw new FunctionException("null?: expects " + cell.getCdr().length + " argument");
        } 
        
        Sexp sexp = MyLisp.apply(cell.getCdr()[0], env);
        if(sexp instanceof Cell){
            Cell cc = (Cell)sexp;
            return Atom.newAtom(cc.getSexps().length == 0?"#t":"#f");
        }else{
            return Atom.newAtom(sexp.toString().isEmpty()?"#t":"#f");
        }
    }
    
}
