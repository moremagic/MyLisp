package mylisp.core;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * symbol Atom
 * @author moremagic
 */
public class AtomSymbol extends Atom{
    private String value;
    AtomSymbol(String value){
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
    
   @Override
    public boolean equals(Object object) {
        if(object instanceof AtomSymbol){
            return ((AtomSymbol)object).value.equals(value);
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }
}
