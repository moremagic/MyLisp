package mylisp.core;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Cell
 * 
 * @deprecated 
 * @author moremagic
 */
public class Cell implements IPair {
    Sexp[] sexps = null;

    public Cell(Sexp car, Sexp[] cdr) {
        this.sexps = new Sexp[cdr.length + 1];
        for (int i = 0; i < this.sexps.length; i++) {
            if (i == 0) {
                this.sexps[i] = car;
            } else {
                this.sexps[i] = cdr[i - 1];
            }
        }
    }

    public Cell(Sexp... sexps) {
        assert sexps.length < 2 : "not cell";
        this.sexps = sexps;
    }

    @Override
    public Sexp getCar() {
        return this.sexps[0];
    }

    @Override
    public Sexp[] getList() {
        return this.sexps;
    }

    @Override
    public String toString() {
        if (this.sexps.length == 0) {
            return "()";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("(").append(getCar());
            for (Sexp s : new ArrayList<Sexp>(Arrays.asList(sexps)).subList(1, sexps.length)) {
                sb.append(" ").append(s.toString());
            }
            sb.append(")");
            return sb.toString();
        }
    }

    @Override
    public Sexp getCdr() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCar(Sexp car) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCdr(Sexp cdr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
