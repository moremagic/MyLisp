package mylisp.core;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Atom class
 * @author mitsu
 */
public abstract class Atom implements Sexp{
    public abstract Object getValue();
    
    public static Atom newAtom(Object value){
        if(value instanceof Number){
            return new AtomNumber((Number)value);
        }else{
            return new AtomStr(value.toString());
        }
    }
}
