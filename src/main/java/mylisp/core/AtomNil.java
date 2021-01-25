/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.core;

/**
 * nil atom class
 * @author moremagic
 */
public class AtomNil extends Atom{   
    @Override
    public Object getValue() {
        return "";
    }
}
