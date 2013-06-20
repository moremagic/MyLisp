package mylisp.core;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * String Atim
 * @author mitsu
 */
public class AtomStr extends Atom{
    private String value;
    AtomStr(String value){
        this.value = value;
    }
    
    @Override
    public String getValue(){
        return this.value;
    }

    @Override
    public String toString() {
        return value;
    }
    
}
