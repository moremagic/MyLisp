package mylisp.core;

import java.math.BigDecimal;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author moremagic
 */
public class AtomNumber extends Atom{
    private Number value;
    AtomNumber(Number value){
        this.value = value;
    }
    
    @Override
    public Number getValue(){
        return value;
    }

    @Override
    public String toString() {
        if(value instanceof BigDecimal){
            return ((BigDecimal)value).toPlainString();
        }else{
            return value.toString();
        }
    }

    @Override
    public boolean equalAtom(Atom atom) {
        if(atom instanceof AtomNumber){
            return ((AtomNumber)atom).value == value;
        }else{
            return false;
        }
    }
}
