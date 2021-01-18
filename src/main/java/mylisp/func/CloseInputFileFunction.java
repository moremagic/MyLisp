/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import mylisp.MyLisp;
import mylisp.core.AbstractOperator;
import mylisp.core.Atom;
import mylisp.core.AtomPort;
import mylisp.core.AtomString;
import mylisp.core.AtomSymbol;
import mylisp.core.IPair;
import mylisp.core.Sexp;

/**
 * close-input-file class
 *
 * @author moremagic
 */
public class CloseInputFileFunction extends AbstractOperator{

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        super.checkArgmunet(cons, 1);

        Sexp cdr = MyLisp.apply(cons.getCdr(), env);
        if(cdr instanceof AtomPort){
            try {
                ((InputStream)((AtomPort)cdr).getValue()).close();
                return Atom.newAtom(true);
            } catch (IOException ex) {
                Logger.getLogger(CloseInputFileFunction.class.getName()).log(Level.SEVERE, null, ex);
                throw new FunctionException(operatorSymbol() + ": cannot close input file: " + new File(((AtomString)cdr).getValue()).getAbsolutePath());
            }
        }else{
            throw new FunctionException(operatorSymbol() + ": expects argument of type <path or port>; given " + cdr);
        }
    }

    @Override
    public String operatorSymbol() {
        return "close-input-file";
    }
}
