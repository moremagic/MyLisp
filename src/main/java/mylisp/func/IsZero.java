/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.math.BigDecimal;
import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.AbstractOperator;
import mylisp.core.Atom;
import mylisp.core.AtomNumber;
import mylisp.core.AtomSymbol;
import mylisp.core.IPair;
import mylisp.core.Sexp;

/**
 * zero? class
 *
 * @author moremagic
 */
public class IsZero extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        super.checkArgument(cons, 1);

        boolean ret = false;
        Sexp sexp = MyLisp.apply(cons.getCdr(), env);

        //TODO: sexp がConsCellになってるので常にFalseになるバグを見つけた
        if(sexp instanceof AtomNumber){
            Number val = ((AtomNumber) sexp).getValue();
            if (val instanceof Integer) {
                ret = ((Integer)val).intValue() == 0;
            } else {
                BigDecimal buf = (val instanceof BigDecimal) ? (BigDecimal) val : new BigDecimal((double) val.doubleValue());
                ret = buf.equals(BigDecimal.ZERO);
            }
        }
        return Atom.newAtom(ret);
    }

    @Override
    public String operatorSymbol() {
        return "zero?";
    }
}
