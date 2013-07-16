package mylisp.core;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Cell
 *
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
    public Sexp[] getCdr() {
        Sexp[] ret = new Sexp[this.sexps.length - 1];
        System.arraycopy(sexps, 1, ret, 0, ret.length);
        return ret;
    }

    @Override
    public Sexp[] getSexps() {
        return this.sexps;
    }

    @Override
    public String toString() {
        if (this.sexps.length == 0) {
            return "()";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("(").append(getCar());
            for (Sexp s : getCdr()) {
                sb.append(" ").append(s.toString());
            }
            sb.append(")");
            return sb.toString();
        }
    }
}
