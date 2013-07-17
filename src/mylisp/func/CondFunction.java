/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.Atom;
import mylisp.core.AtomBoolean;
import mylisp.core.Cell;
import mylisp.core.IPair;
import mylisp.core.Sexp;

/**
 * Cond class
 *
 * @author moremagic
 */
public class CondFunction implements IFunction {

    @Override
    public Sexp eval(Cell cell, Map<String, Sexp> env) throws FunctionException {
        Sexp ret = Atom.newAtom("");
        for (int i = 0; i < cell.getCdr().length; i++) {
            Sexp cdr = cell.getCdr()[i];
            if (cdr instanceof IPair) {
                IPair ccc = (IPair) cdr;
                Sexp cccCar = MyLisp.eval(ccc.getCar(), env);
                if (!cccCar.toString().equals(AtomBoolean.F)) {
                    if (ccc.getSexps().length == 1) {
                        ret = MyLisp.eval(cccCar, env);
                        break;
                    } else if (ccc.getSexps().length == 2) {
                        ret = MyLisp.eval(ccc.getCdr()[0], env);
                        break;
                    } else {
                        throw new FunctionException("procedure application: expected procedure");
                    }
                } else if (i == cell.getCdr().length - 1) {
                    //末尾再帰Flag ON
                    MyLisp.tailCall = true;
                    if (ccc.getCar().toString().equals("else")) {
                        ret = ccc.getCdr()[2];
                    } else if (!cccCar.toString().equals(AtomBoolean.F)) {
                        if (ccc.getCdr().length != 0 && !cccCar.toString().equals(AtomBoolean.F)) {
                            ret = ccc.getCdr()[0];
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
    public String functionSymbol() {
        return "cond";
    }
}
