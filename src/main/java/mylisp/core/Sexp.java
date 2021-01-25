package mylisp.core;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * S exp Interface
 * @author moremagic
 */
public interface Sexp{
    public Sexp[] getList();  
    
    @Override
    public abstract String toString();
}
