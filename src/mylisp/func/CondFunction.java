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
            if (cdr instanceof Cell) {
                Cell ccc = (Cell) cdr;
                Sexp cccCar = MyLisp.eval(ccc.getCar(), env);
                if (!cccCar.toString().equals(AtomBoolean.F)) {
                    if (ccc.getCdr().length == 1) {
                        ret = MyLisp.apply(cccCar, env);
                        break;
                    } else if (ccc.getCdr().length == 2) {
                        ret = MyLisp.apply(ccc.getCdr()[0], env);
                        break;
                    } else {
                        throw new FunctionException("procedure application: expected procedure");
                    }
                } else if (i == cell.getCdr().length - 1) {
                    if (ccc.getCar().toString().equals("else")) {
                        ret = MyLisp.apply(ccc.getCdr()[0], env);
                    } else if (!cccCar.toString().equals(AtomBoolean.F)) {
                        if (ccc.getCdr().length != 0 && !cccCar.toString().equals(AtomBoolean.F)) {
                            ret = MyLisp.apply(ccc.getCdr()[0], env);
                        } else {
                            ret = MyLisp.apply(cccCar, env);
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
