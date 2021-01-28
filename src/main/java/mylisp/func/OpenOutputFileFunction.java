/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import mylisp.MyLisp;
import mylisp.core.AbstractOperator;
import mylisp.core.Atom;
import mylisp.core.AtomString;
import mylisp.core.AtomSymbol;
import mylisp.core.IPair;
import mylisp.core.Sexp;

/**
 * open-output-file class
 *
 * @author moremagic
 */
public class OpenOutputFileFunction extends AbstractOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        super.checkArgument(cons, 1);

        Sexp cdr = MyLisp.apply(cons.getCdr(), env);
        if (cdr instanceof AtomString) {
            try {
                return Atom.newAtom(new FileOutputStream(new File(((AtomString) cdr).getValue())));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(OpenOutputFileFunction.class.getName()).log(Level.SEVERE, null, ex);
                throw new FunctionException(operatorSymbol() + ": cannot open input file: " + new File(((AtomString) cdr).getValue()).getAbsolutePath());
            }
        } else {
            throw new FunctionException(operatorSymbol() + ": expects argument of type <path or string>; given " + cdr);
        }
    }

    @Override
    public String operatorSymbol() {
        return "open-output-file";
    }
}
