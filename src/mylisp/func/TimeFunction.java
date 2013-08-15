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
 * time class
 * @author moremagic
 */
public class TimeFunction extends AbstractOperator{

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        long start_time = System.currentTimeMillis();
        Sexp ret = MyLisp.eval(cons.getCdr(), env);
        System.out.println((System.currentTimeMillis() - start_time) + "[ms]");
        return ret;
    }
    
    @Override
    public String operatorSymbol() {
        return "time";
    }

}
