/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import mylisp.core.Operator;
import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.AtomSymbol;
import mylisp.core.Cell;
import mylisp.core.Sexp;

/**
 * display class
 *
 * @author moremagic
 */
public class DisplayFunction implements Operator {

    @Override
    public Sexp eval(Cell cell, Map<AtomSymbol, Sexp> env) throws FunctionException {
        if (cell.getCdr().length != 1) {
            throw new FunctionException("display: expects 1 to 2 arguments, given " + cell.getCdr().length);
        }

        Sexp ret = MyLisp.apply(cell.getCdr()[0], env);
        System.out.print( ret.toString() );
        return ret;
    }

    @Override
    public String operatorSymbol() {
        return "display";
    }
}
