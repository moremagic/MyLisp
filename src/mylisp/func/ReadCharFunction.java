/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.io.IOException;
import java.io.InputStream;
import mylisp.core.Operator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import mylisp.core.Atom;
import mylisp.core.AtomPort;
import mylisp.core.AtomSymbol;
import mylisp.core.Cell;
import mylisp.core.Sexp;

/**
 * read-char Function
 *
 * @author moremagic
 */
public class ReadCharFunction implements Operator {

    @Override
    public Sexp eval(Cell cell, Map<AtomSymbol, Sexp> env) throws FunctionException {
        if (cell.getCdr().length != 1) {
            throw new FunctionException(operatorSymbol() + ": expects 1 argument, given " + cell.getCdr().length);
        }

        Sexp ret = null;
        
        Sexp cdr = cell.getCdr()[0];
        if (cdr instanceof AtomPort && ((AtomPort) cdr).getValue() instanceof InputStream) {
            AtomPort port = (AtomPort) cdr;
            try {
                char c = (char) ((InputStream) port.getValue()).read();
                ret = Atom.newAtom(new String(new char[]{c}));
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
