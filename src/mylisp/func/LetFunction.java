/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.core.Atom;
import mylisp.core.AtomSymbol;
import mylisp.core.Cell;
import mylisp.core.IPair;
import mylisp.core.Lambda;
import mylisp.core.Sexp;
import mylisp.core.SpecialOperator;
import mylisp.core.TailCallOperator;

/**
 * Let class
 *
 * @author moremagic
 */
public class LetFunction implements SpecialOperator {

    @Override
    public Sexp eval(Cell cell, Map<AtomSymbol, Sexp> env) throws FunctionException {
        Sexp env_list = null;
        Sexp lambda_body = null;

        if (cell.getCdr()[0] instanceof IPair) {
            //名前なしLet
            env_list = cell.getCdr()[0];
            lambda_body = cell.getCdr()[1];
        } else {
            //名前付きLet
            env_list = cell.getCdr()[1];
            lambda_body = cell.getCdr()[2];
        }

        if (env_list instanceof IPair && lambda_body instanceof IPair) {
            Sexp[] ll = ((IPair) env_list).getSexps();
            Sexp[] keys = new Sexp[ll.length];
            Sexp[] values = new Sexp[ll.length];
            for (int i = 0; i < ll.length; i++) {
                if (ll[i] instanceof IPair) {
                    IPair buf = (IPair) ll[i];
                    keys[i] = buf.getCar();
                    values[i] = buf.getCdr()[0];
                } else {
                    throw new FunctionException("let: bad syntax in: " + cell.toString());
                }
            }

            if (cell.getCdr()[0] instanceof IPair) {
                //名前なしLet
                Lambda lambda = new Lambda(Atom.newAtom(Lambda.LAMBDA_SYMBOL), new Cell(keys), lambda_body);

                //末尾再帰最適化
                return TailCallOperator.resurveTailCall(new Cell(lambda, values), env);
            } else {
                //名前ありLet     
                Lambda lambda = new Lambda(Atom.newAtom(Lambda.LAMBDA_SYMBOL), new Cell(keys), lambda_body);
                env.put((AtomSymbol) cell.getCdr()[0], lambda);

                //末尾再帰最適化
                return TailCallOperator.resurveTailCall(new Cell(cell.getCdr()[0], values), env);
            }
        } else {
            throw new FunctionException("let: bad syntax in: " + cell.toString());
        }
    }

    @Override
    public String operatorSymbol() {
        return "let";
    }
}
