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
 * or class
 * @author moremagic
 */
public class OrFunction implements IFunction{

    @Override
    public Sexp eval(Cell cell, Map<String, Sexp> env) throws FunctionException {
        for(Sexp sexp: cell.getCdr()){
            Sexp sexp_apply = MyLisp.apply(sexp, env);
            if(sexp_apply.toString().equals("#t")){
                return Atom.newAtom("#t");
            }
        }
        return Atom.newAtom("#f");
    }
    
}
