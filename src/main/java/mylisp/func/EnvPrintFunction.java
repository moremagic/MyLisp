/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.core.AbstractOperator;
import mylisp.core.AtomSymbol;
import mylisp.core.IPair;
import mylisp.core.Lambda;
import mylisp.core.Sexp;

/**
 * EnvPrintFunction class
 *
 * @author moremagic
 */
public class EnvPrintFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        System.out.println("<<env-list>>");
        for (Map.Entry<AtomSymbol, Sexp> item : env.entrySet()) {
            System.out.println("[" + item.getKey() + " : " + item.getValue() + "] , ");
        }
        return cons;
    }

    @Override
    public String operatorSymbol() {
        return "env-print";
    }
}
