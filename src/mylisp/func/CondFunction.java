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
import mylisp.core.Cell;
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
    public Sexp eval(Cell cell, Map<AtomSymbol, Sexp> env) throws FunctionException {
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
                    } else {
                        for(int j = 0 ; j < ccc.getCdr().length ; j++){
                            if(j == ccc.getCdr().length-1){
                                //末尾再帰Flag ON
                                MyLisp.tailCall = true;
                                ret = ccc.getCdr()[j];
                            }else{
                                ret = MyLisp.eval(ccc.getCdr()[j], env);
                            }
                        }
                        break;
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
    public String operatorSymbol() {
        return "cond";
    }
}
