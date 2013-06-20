/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.core;

/**
 * boolean atom class
 * @author mitsu
 */
public class AtomBoolean extends Atom{
    private boolean value;
    
    AtomBoolean(String value){
        this.value = value.equals("#t")?true:false;
    }
    
    @Override
    public Object getValue() {
        return value?"#t":"#f";
    }
    
    public String toString(){
        return value?"#t":"#f";
    }    
}
