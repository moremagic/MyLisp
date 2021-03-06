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

    public ConsCell() {
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

    /**
     * ConsCell表現からフラットなSexp配列を取得する
     * メソッド createConsCell(Sexp[]) と逆の動きをします
     * <p>
     * TODO:フラットなSexpを作れないConsCellがある気がするので論理的にバグがありそう
     *
     * @return フラットなSexp配列
     */
    @Override
    public Sexp[] getList() {
        List<Sexp> ret = new ArrayList<>();

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

        StringBuilder sb = new StringBuilder();
        sb.append(cons.getCar());
        if (cons.getCdr() instanceof Atom && cons.getCdr() != AtomNil.INSTANCE) {
            //Cdr が Atom の場合は Dotpair 表示を行うが Nil の場合は Dotpair 表示を行わない
            sb.append(" . ").append(cons.getCdr());
        } else if (cons.getCdr() instanceof IPair) {
            sb.append(" ").append(createConsString((IPair) cons.getCdr()));
        }
        return sb.toString();
    }


    /**
     * Sexp配列からConsCellを生成する
     *
     * @param sexps sexp配列
     * @return ConsCell
     */
    public static ConsCell createConsCell(Sexp[] sexps) {
        if (sexps.length == 1) {
            return new ConsCell(sexps[0], AtomNil.INSTANCE);
        } else {
            return new ConsCell(sexps[0], createConsCell(Arrays.copyOfRange(sexps, 1, sexps.length)));
        }
    }
}
