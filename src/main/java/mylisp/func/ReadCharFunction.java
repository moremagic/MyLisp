/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import mylisp.MyLisp;
import mylisp.core.AbstractOperator;
import mylisp.core.AtomChar;
import mylisp.core.AtomPort;
import mylisp.core.AtomSymbol;
import mylisp.core.IPair;
import mylisp.core.Sexp;

/**
 * read-char Function
 *
 * @author moremagic
 */
public class ReadCharFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
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
