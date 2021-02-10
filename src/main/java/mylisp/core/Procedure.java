package mylisp.core;

public interface Procedure {
    String procedureSymbol();
    Sexp apply(Sexp sexp) throws ProcedureException;

    class ProcedureException extends MyLispException {
        public ProcedureException(String message) {
            super(message);
        }
    }
}
