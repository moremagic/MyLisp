/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.AtomBoolean;
import mylisp.core.AtomSymbol;
import mylisp.core.Cell;
import mylisp.core.Sexp;
import mylisp.core.SpecialOperator;
import mylisp.core.TailCallOperator;

/**
 * IF class
 *
 * @author moremagic
 */
public class IFFunction implements SpecialOperator {

    @Override
    public Sexp eval(Cell cell, Map<AtomSymbol, Sexp> env) throws FunctionException {
        if (cell.getCdr().length < 3) {
            throw new FunctionException("if: bad syntax (has 1 part after keyword) in: " + cell.toString());
        }

        Sexp sexp = MyLisp.apply(cell.getCdr()[0], env);
        if (sexp instanceof AtomBoolean && sexp.toString().equals(AtomBoolean.F)) {
            //末尾再帰コード
            return TailCallOperator.reserveTailCall(cell.getCdr()[2], env);
        } else {
            return MyLisp.eval(cell.getCdr()[1], env);
        }
    }

    @Override
    public String operatorSymbol() {
        return "if";
    }
}
