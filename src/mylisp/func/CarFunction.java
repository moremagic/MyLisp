/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.AbstractOperator;
import mylisp.core.AtomSymbol;
import mylisp.core.IPair;
import mylisp.core.Sexp;

/**
 * car class
 *
 * @author moremagic
 */
public class CarFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        super.checkArgmunet(cons, 1);

        Sexp cdr = MyLisp.apply(cons.getCdr(), env);
        if (cdr instanceof IPair) {
            return ((IPair) cdr).getCar();
        } else {
            throw new FunctionException(operatorSymbol() + ": expects argument of type <pair>; given " + cdr);
        }
    }

    @Override
    public String operatorSymbol() {
        return "car";
    }
}
