package mylisp.func;

import mylisp.core.*;

import java.util.Map;

/**
 * EnvPrintFunction class
 *
 * @author moremagic
 */
public class EnvPrintFunction extends AbstractOperator {

    @Override
    public Sexp apply(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
        System.out.println("<<env-list>>");
        for (Map.Entry<AtomSymbol, Sexp> item : env.entrySet()) {
            System.out.println("[" + item.getKey() + " : " + item.getValue() + "] , ");
        }
        return cons;
    }

    @Override
    public String operatorSymbol() {
        return "print-env";
    }
}
