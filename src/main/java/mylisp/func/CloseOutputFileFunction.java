package mylisp.func;

import mylisp.MyLisp;
import mylisp.core.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * close-output-file class
 *
 * @author moremagic
 */
public class CloseOutputFileFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
        super.checkArgument(cons, 1);

        Sexp cdr = MyLisp.apply(cons.getCdr(), env);
        if (cdr instanceof AtomPort) {
            try {
                ((OutputStream) ((AtomPort) cdr).getValue()).close();
                return Atom.newAtom(true);
            } catch (IOException ex) {
                Logger.getLogger(CloseOutputFileFunction.class.getName()).log(Level.SEVERE, null, ex);
                throw new FunctionException(operatorSymbol() + ": cannot close input file: " + new File(((AtomString) cdr).getValue()).getAbsolutePath());
            }
        } else {
            throw new FunctionException(operatorSymbol() + ": expects argument of type <path or port>; given " + cdr);
        }
    }

    @Override
    public String operatorSymbol() {
        return "close-output-file";
    }
}
