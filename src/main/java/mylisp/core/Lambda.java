package mylisp.core;

import mylisp.MyLisp;

import java.util.*;

/**
 * Lambda
 *
 * @author moremagic
 */
public class Lambda implements IPair {
    public static final String LAMBDA_SYMBOL = "lambda";
    private final ConsCell consCell;

    public Lambda(Sexp car, Sexp cdr) {
        assert car.toString().equals(LAMBDA_SYMBOL) : "car for lambda pair must be 'lambda'";
        consCell = new ConsCell(car, cdr);
    }

    public Sexp lambdaEvals(Map<AtomSymbol, Sexp> env, Sexp[] value) throws MyLispException {
        Sexp[] keys = ((IPair) getCdr()).getCar().getList();//cdar
        Sexp cddr = ((IPair) getCdr()).getCdr();

        //環境のシャローコピー
        Map<AtomSymbol, Sexp> newEnv = new HashMap<>(env);
        for (int i = 0; i < keys.length; i++) {
            newEnv.put((AtomSymbol) keys[i], MyLisp.eval(value[i], env));
        }

        return MyLisp.eval(cddr, newEnv);
    }

    @Override
    public void setCar(Sexp car) {
        consCell.setCar(car);
    }

    @Override
    public Sexp getCar() {
        return consCell.getCar();
    }

    @Override
    public Sexp getCdr() {
        return consCell.getCdr();
    }

    @Override
    public void setCdr(Sexp cdr) {
        consCell.setCdr(cdr);
    }

    @Override
    public Sexp[] getList() {
        return consCell.getList();
    }

    @Override
    public String toString() {
        return consCell.toString();
    }
}
