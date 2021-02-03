package mylisp.func;

import mylisp.MyLisp;
import mylisp.core.*;

import java.util.Map;

/**
 * ビットシフト関数
 *
 * @author moremagic
 */
public class AshFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
        super.checkArgument(cons, 2);

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
