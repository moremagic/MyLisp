/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.Atom;
import mylisp.core.AtomBoolean;
import mylisp.core.AtomSymbol;
import mylisp.core.ConsCell;
import mylisp.core.IPair;
import mylisp.core.Sexp;
import mylisp.core.SpecialOperator;

/**
 * Cond class
 *
 * @author moremagic
 */
public class CondFunction implements SpecialOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        Sexp ret = Atom.NIL;
        
        Sexp[] buf = cons.getCdr().getList();
        for (int i = 0; i < buf.length; i++) {
            if (buf[i] instanceof IPair) {
                IPair cdr = (IPair) buf[i];
                Sexp cadr = MyLisp.eval(cdr.getCar(), env);
                Sexp cddr = cdr.getCdr();

                if (cadr == AtomBoolean.AtomTrue || (i == buf.length - 1 && cadr.toString().equals("else"))) {
                    ret = MyLisp.evals(cddr, env);
                    break;
                }
            } else {
                throw new FunctionException("cond: bad syntax (clause is not a test-value pair) in: " + buf[i].toString());
            }
        }
        return ret;
    }

    @Override
    public String operatorSymbol() {
        return "cond";
    }
}
