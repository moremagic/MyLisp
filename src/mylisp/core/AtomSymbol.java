package mylisp.core;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * symbol Atim
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
    public boolean equalAtom(Atom atom) {
        if(atom instanceof AtomSymbol){
            return ((AtomSymbol)atom).value.equals(value);
        }else{
            return false;
        }
    }
}
