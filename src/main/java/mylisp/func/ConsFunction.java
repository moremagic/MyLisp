package mylisp.func;

import mylisp.MyLisp;
import mylisp.core.*;

import java.util.Map;

/**
 * cons Function
 *
 * @author moremagic
 */
public class ConsFunction extends AbstractOperator {
    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
        super.checkArgument(cons, 2);

        Sexp[] list = cons.getCdr().getList();

        Sexp car = MyLisp.apply(list[0], env);
        if (car instanceof IPair) {
            ((IPair) car).setCdr(MyLisp.apply(list[1], env));
            return car;
        } else {
            return new ConsCell(car, MyLisp.apply(list[1], env));
        }
    }


    @Override
    public String operatorSymbol() {
        return "cons";
    }

}
