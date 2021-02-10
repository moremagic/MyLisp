package mylisp.func;

import mylisp.core.AbstractOperator;
import mylisp.core.IPair;
import mylisp.core.Procedure;
import mylisp.core.Sexp;

public class CarProcedure implements Procedure {
    @Override
    public String procedureSymbol() {
        return "car";
    }

    @Override
    public Sexp apply(Sexp sexp) {
        return null;
    }
}
