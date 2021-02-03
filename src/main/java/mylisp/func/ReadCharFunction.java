package mylisp.func;

import mylisp.MyLisp;
import mylisp.core.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * read-char Function
 *
 * @author moremagic
 */
public class ReadCharFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
        super.checkArgument(cons, 1);

        Sexp ret = null;
        Sexp cdr = MyLisp.apply(cons.getCdr(), env);
        if (cdr instanceof AtomPort && ((AtomPort) cdr).getValue() instanceof InputStream) {
            AtomPort port = (AtomPort) cdr;
            try {
                char c = (char) ((InputStream) port.getValue()).read();
                ret = new AtomChar(c);
            } catch (IOException ex) {
                Logger.getLogger(ReadCharFunction.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            throw new FunctionException(operatorSymbol() + ": expects argument of type <input-port>; given " + cdr);
        }

        return ret;
    }

    @Override
    public String operatorSymbol() {
        return "read-char";
    }
}
