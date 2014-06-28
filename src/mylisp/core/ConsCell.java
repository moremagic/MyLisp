/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * cons cell
 *
 * @author moremagic
 */
public class ConsCell implements IPair {
    public static final ConsCell NIL = new ConsCell();
    private Sexp car = NIL;
    private Sexp cdr = NIL;

    private ConsCell() {
        //NIL Object
    }

    public ConsCell(Sexp car, Sexp cdr) {
        this.car = car;
        this.cdr = cdr;
    }

    /**
     * @return the car
     */
    @Override
    public Sexp getCar() {
        return car;
    }

    /**
     * @param car the car to set
     */
    @Override
    public void setCar(Sexp car) {
        this.car = car;
    }

    /**
     * @return the cdr
     */
    @Override
    public Sexp getCdr() {
        assert cdr == null:"exception format!";
        return cdr;
    }

    /**
     * @param cdr the cdr to set
     */
    @Override
    public void setCdr(Sexp cdr) {
        this.cdr = cdr;
    }
    
    @Override
    public Sexp[] getList() {
        List<Sexp> ret = new ArrayList<Sexp>();

        ret.add(this.car);
        if (this.cdr != null && this.cdr != NIL) {
            ret.addAll(Arrays.asList(this.cdr.getList()));
        }

        return ret.toArray(new Sexp[0]);
    }

    @Override
    public String toString() {
        if (this == NIL) {
            return "()";
        }
        
        return "(" + createConsString(this) + ")";
    }

    
    private static String createConsString(IPair cons){
        StringBuffer sb = new StringBuffer();
        sb.append(cons.getCar());
        if(cons.getCdr() == ConsCell.NIL){
            //NOP
        }else if(cons.getCdr() instanceof Atom){
            sb.append(" . ").append(cons.getCdr());
        }else if(cons.getCdr() instanceof IPair){
            sb.append(" ").append( createConsString((IPair)cons.getCdr()) );
        }
        return sb.toString();
    }
    
    
    public static Sexp list2Cons(Sexp[] list) {
        if (list.length == 0) {
            return NIL;
        } else {
            Sexp ret = new ConsCell(list[0], list2Cons(Arrays.asList(list).subList(1, list.length).toArray(new Sexp[0])));
            return ret;
        }
    }
}
