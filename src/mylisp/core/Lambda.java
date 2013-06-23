package mylisp.core;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Cell
 * @author mitsu
 */
public class Lambda extends Cell{
    public Lambda(Sexp car, Sexp[] cdr){
        super(car, cdr);
    }
        
    public Lambda(Sexp... sexps){
        super(sexps);
    }
    
//    @Override
//    public String toString(){
//        StringBuilder sb = new StringBuilder();
//        sb.append("<#function ").append(getClass().getName()).append("@").append(Integer.toHexString(hashCode()));
//        sb.append(">");
//        return sb.toString();
//    }
}
