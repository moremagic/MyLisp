package mylisp.func;

import mylisp.MyLisp;
import mylisp.core.*;

import java.util.Map;

/**
 * IF class
 *
 * @author moremagic
 */
public class IFFunction implements SpecialOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
        if (cons.getCdr().getList().length < 3) {
            throw new AbstractOperator.FunctionException("if: bad syntax (has 1 part after keyword) in: " + cons.toString());
        }

        Sexp[] list = cons.getCdr().getList();
        Sexp sexp = MyLisp.apply(list[0], env);
        if (sexp == AtomBoolean.AtomFalse) {
            //末尾再帰コード
            return TailCallOperator.reserveTailCall(list[2], env);
        } else {
            return MyLisp.eval(list[1], env);
        }
    }

    @Override
    public String operatorSymbol() {
        return "if";
    }
}
