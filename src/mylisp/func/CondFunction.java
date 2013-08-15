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
        for (int i = 0; i < cons.getCdr().getList().length; i++) {
            Sexp cdr = cons.getCdr().getList()[i];
            if (cdr instanceof IPair) {
                IPair ccc = (IPair) cdr;
                Sexp cccCar = MyLisp.eval(ccc.getCar(), env);
                if (!cccCar.toString().equals(AtomBoolean.F)) {
                    if (ccc.getList().length == 1) {
                        ret = MyLisp.eval(cccCar, env);
                        break;
                    } else {
                        for (int j = 0; j < ccc.getCdr().getList().length; j++) {
                            if (j == ccc.getCdr().getList().length - 1) {
                                //末尾再帰最適化
                                ret = TailCallOperator.reserveTailCall(ccc.getCdr().getList()[j], env);
                            } else {
                                ret = MyLisp.eval(ccc.getCdr().getList()[j], env);
                            }
                        }
                        break;
                    }
                } else if (i == cons.getCdr().getList().length - 1) {
                    if (ccc.getCar().toString().equals("else")) {
                        //末尾再帰最適化
                        ret = TailCallOperator.reserveTailCall(ccc.getCdr().getList()[2], env);
                   } else if (!cccCar.toString().equals(AtomBoolean.F)) {
                        if (ccc.getCdr().getList().length != 0 && !cccCar.toString().equals(AtomBoolean.F)) {
                            //末尾再帰最適化
                            ret = TailCallOperator.reserveTailCall(ccc.getCdr().getList()[0], env);
                        } else {
                            ret = cccCar;
                        }
                    }
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
