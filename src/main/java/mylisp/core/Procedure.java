package mylisp.core;

public interface Procedure {
    String procedureSymbol();
    Sexp apply(Sexp sexp);
}
