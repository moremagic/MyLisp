/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.core;

/**
 * boolean atom class
 * @author moremagic
 */
public class AtomBoolean extends Atom{
    public static String T = "#t";
    public static String F = "#f";
    public static AtomBoolean AtomTrue = new AtomBoolean(T);
    public static AtomBoolean AtomFalse = new AtomBoolean(F);

    
    private boolean value;
    private AtomBoolean(String value){
        this.value = value.equals(T)?true:false;
    }
 
    public static AtomBoolean createAtomBoolean(boolean b){
        return b?AtomTrue:AtomFalse;
    }
    
    @Override
    public Object getValue() {
        return value?T:F;
    }
    
    public String toString(){
        return value?T:F;
    }    

    @Override
    public boolean equalAtom(Atom atom) {
        if(atom instanceof AtomBoolean){
            return ((AtomBoolean)atom).value == value;
        }else{
            return false;
        }
    }

}
