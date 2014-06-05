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
import mylisp.core.IPair;
import mylisp.core.Sexp;
import mylisp.core.SpecialOperator;
import mylisp.core.TailCallOperator;

/**
 * Cond class
 *
 * @author moremagic
 */
public class CondFunction implements SpecialOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        Sexp ret = Atom.newAtom("");
        
        Sexp[] buf = cons.getCdr().getList();
        for (int i = 0; i < buf.length; i++) {
            Sexp cdr = buf[i];   
            if (cdr instanceof IPair) {
                IPair ccc = (IPair) cdr;
                Sexp cccCar = MyLisp.eval(ccc.getCar(), env);
                if (!cccCar.toString().equals(AtomBoolean.F)) {
                    Sexp[] cccBuf = ccc.getCdr().getList();
                    for (int j = 0; j < cccBuf.length; j++) {
                        if (j == cccBuf.length - 1) {
                            //末尾再帰最適化
                            ret = TailCallOperator.reserveTailCall(cccBuf[j], env);
                        } else {
                            ret = MyLisp.eval(cccBuf[j], env);
                        }
                    }
                    break;
                } else if (i == buf.length - 1) {
                    //末尾再帰最適化
                    ret = TailCallOperator.reserveTailCall(ccc.getCdr().getList()[0], env);
                }
            } else {
                throw new FunctionException("cond: bad syntax (clause is not a test-value pair) in: " + cdr.toString());
            }
        }
        return ret;
    }

    @Override
    public String operatorSymbol() {
        return "cond";
    }
}
