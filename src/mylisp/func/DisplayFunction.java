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
 * display class
 *
 * @author moremagic
 */
public class DisplayFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        super.checkArgmunet(cons, 1);

        Sexp ret = MyLisp.apply(cons.getCdr(), env);
        System.out.print( ret.toString() );
        return ret;
    }

    @Override
    public String operatorSymbol() {
        return "display";
    }
}
