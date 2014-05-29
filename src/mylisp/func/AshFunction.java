/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.AbstractOperator;
import mylisp.core.Atom;
import mylisp.core.AtomNumber;
import mylisp.core.AtomSymbol;
import mylisp.core.IPair;
import mylisp.core.Sexp;

/**
 * ビットシフト関数
 *
 * @author moremagic
 */
public class AshFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        super.checkArgmunet(cons, 2);

        Sexp[] list = cons.getCdr().getList();
        long sexp_apply1 = ((AtomNumber) MyLisp.apply(list[0], env)).getValue().longValue();
        long sexp_apply2 = ((AtomNumber) MyLisp.apply(list[1], env)).getValue().longValue();

        Sexp ret;
        if (sexp_apply2 < 0L) {
            ret = Atom.newAtom(sexp_apply1 >> (sexp_apply2 * -1));
        } else {
            ret = Atom.newAtom(sexp_apply1 << sexp_apply2);
        }

        return ret;
    }

    @Override
    public String operatorSymbol() {
        return "ash";
    }
}