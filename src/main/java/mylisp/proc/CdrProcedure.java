package mylisp.proc;

import mylisp.core.IPair;
import mylisp.core.Procedure;
import mylisp.core.Sexp;

public class CdrProcedure implements Procedure {
    @Override
    public String procedureSymbol() {
        return "cdr";
    }

    @Override
    public Sexp apply(Sexp sexp) throws ProcedureException {
        checkArgument(sexp);
        return ((IPair) sexp).getCdr();
    }

    public void checkArgument(Sexp sexp) throws ProcedureException {
        if(!(sexp instanceof IPair)){
            throw new ProcedureException(String.format("not array [%s] is not a function [%s]", sexp, procedureSymbol()));
        }
    }
}
