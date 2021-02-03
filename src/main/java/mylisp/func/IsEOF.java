package mylisp.func;

import mylisp.MyLisp;
import mylisp.core.*;

import java.util.Map;

/**
 * eof-object? class
 *
 * @author moremagic
 */
public class IsEOF extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
        super.checkArgument(cons, 1);

        Sexp sexp = MyLisp.apply(cons.getCdr().getList()[0], env);
        return Atom.newAtom(sexp.toString().equals(new String(new char[]{'\uffff'})));
    }

    @Override
    public String operatorSymbol() {
        return "eof-object?";
    }
}
