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
    private Sexp car = AtomNil.INSTANCE;
    private Sexp cdr = AtomNil.INSTANCE;

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
        assert cdr != null : "cdr for cons cell must be not null.";
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
    /**
     * TODO: このメソッドいる？
     */
    public Sexp[] getList() {
        List<Sexp> ret = new ArrayList<Sexp>();

        ret.add(this.car);
        if (this.cdr != null && this.cdr != AtomNil.INSTANCE) {
            ret.addAll(Arrays.asList(this.cdr.getList()));
        }

        return ret.toArray(new Sexp[0]);
    }

    @Override
    public String toString() {
        return "(" + createConsString(this) + ")";
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof IPair) {
            IPair actual = (IPair) object;
            return actual.getCar().equals(this.car) && actual.getCdr().equals(this.cdr);
        }
        return false;
    }

    private static String createConsString(IPair cons) {
        if (cons.getCar() == AtomNil.INSTANCE) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        sb.append(cons.getCar());
        if (cons.getCdr() == AtomNil.INSTANCE) {
            //NOP
        } else if (cons.getCdr() instanceof Atom) {
            sb.append(" . ").append(cons.getCdr());
        } else if (cons.getCdr() instanceof IPair) {
            sb.append(" ").append(createConsString((IPair) cons.getCdr()));
        }
        return sb.toString();
    }


    /**
     * TODO: LetFunctionでしか使われていないのでLetFunctionで実装されるべき
     *
     * @param list
     * @return
     */
    public static Sexp list2Cons(Sexp[] list) {
        if (list.length == 0) {
            return AtomNil.INSTANCE;
        } else {
            Sexp ret = new ConsCell(list[0], list2Cons(Arrays.asList(list).subList(1, list.length).toArray(new Sexp[0])));
            return ret;
        }
    }
}
