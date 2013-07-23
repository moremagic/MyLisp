package mylisp.core;

import java.io.InputStream;
import java.io.OutputStream;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * symbol Atom
 * @author moremagic
 */
public class AtomPort extends Atom{
    private InputStream input_value = null;
    private OutputStream output_value = null;
    
    AtomPort(InputStream input){
        this.input_value = input;
    }
    
    AtomPort(OutputStream output){
        this.output_value = output;
    }
    
    @Override
    public Object getValue(){
        return (input_value != null)?input_value: output_value;
    }

    @Override
    public String toString() {
        return getValue().toString();
    }
    
   @Override
    public boolean equals(Object object) {
        if(object instanceof AtomPort){
            return ((AtomPort)object).getValue().equals(getValue());
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (this.input_value != null ? this.input_value.hashCode() : 0);
        hash = 17 * hash + (this.output_value != null ? this.output_value.hashCode() : 0);
        return hash;
    }
}
