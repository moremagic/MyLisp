package mylisp.func;

import mylisp.MyLisp;
import mylisp.core.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * open-input-file class
 *
 * @author moremagic
 */
public class OpenInputFileFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
        super.checkArgument(cons, 1);

        Sexp cdr = MyLisp.apply(cons.getCdr(), env);
        if (cdr instanceof AtomString) {
            try {
                return Atom.newAtom(new FileInputStream(new File(((AtomString) cdr).getValue())));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(OpenInputFileFunction.class.getName()).log(Level.SEVERE, null, ex);
                throw new FunctionException(operatorSymbol() + ": cannot open input file: " + new File(((AtomString) cdr).getValue()).getAbsolutePath());
            }
        } else {
            throw new FunctionException(operatorSymbol() + ": expects argument of type <path or string>; given " + cdr);
        }
    }

    @Override
    public String operatorSymbol() {
        return "open-input-file";
    }
}
