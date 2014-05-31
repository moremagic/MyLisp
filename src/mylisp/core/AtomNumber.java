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
    public boolean equals(Object object) {
        if(object instanceof AtomNumber){
            Number buf = ((AtomNumber)object).value;
            if (value instanceof Integer && buf instanceof Integer) {
                return ((Integer)value).intValue() == buf.intValue();
            } else {
                BigDecimal vb = (value instanceof BigDecimal) ? (BigDecimal) value : new BigDecimal(value.toString());
                BigDecimal bb = (buf instanceof BigDecimal)   ? (BigDecimal) buf   : new BigDecimal(buf.toString());
                return vb.equals(bb);
//                return false;
            }            
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }
}
