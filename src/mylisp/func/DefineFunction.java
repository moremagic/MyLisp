/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.Atom;
import mylisp.core.AtomSymbol;
import mylisp.core.Cell;
import mylisp.core.IPair;
import mylisp.core.Lambda;
import mylisp.core.Sexp;

/**
 * define Function
 *
 * @author moremagic
 */
public class DefineFunction implements IFunction {

    @Override
    public Sexp eval(Cell cell, Map<AtomSymbol, Sexp> env) throws FunctionException {
        Sexp[] cdrs = cell.getCdr();
        Sexp ret = null;
        if (cdrs.length == 2) {
            if (cdrs[0] instanceof IPair) {
                Sexp car = ((IPair) cdrs[0]).getCar();
                Sexp[] cdr = ((IPair) cdrs[0]).getCdr();

                //Function生成時の構文糖衣          
                ret = env.put((AtomSymbol) car, new Lambda(Atom.newAtom(Lambda.LAMBDA_SYMBOL), new Sexp[]{new Cell(cdr), cdrs[1]}));
            } else {
                Sexp ss = MyLisp.apply(cdrs[1], env);
                ret = env.put((AtomSymbol) cdrs[0], ss);
            }
        } else {
            throw new FunctionException("define: expects arguments");
        }

        return ret;
    }

    @Override
    public String functionSymbol() {
        return "define";
    }
}
