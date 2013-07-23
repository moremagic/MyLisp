/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import mylisp.core.Operator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import mylisp.MyLisp;
import mylisp.core.Atom;
import mylisp.core.AtomString;
import mylisp.core.AtomSymbol;
import mylisp.core.Cell;
import mylisp.core.Sexp;

/**
 * open-input-file class
 *
 * @author moremagic
 */
public class OpenInputFileFunction implements Operator {

    @Override
    public Sexp eval(Cell cell, Map<AtomSymbol, Sexp> env) throws FunctionException {
        if (cell.getCdr().length != 1) {
            throw new FunctionException(operatorSymbol() + ": expects 1 argument, given " + cell.getCdr().length);
        }

        Sexp cdr = MyLisp.apply(cell.getCdr()[0], env);
        if(cdr instanceof AtomString){
            try {
                return Atom.newAtom(new FileInputStream( new File(((AtomString)cdr).getValue()) ) );
            } catch (FileNotFoundException ex) {
                Logger.getLogger(OpenInputFileFunction.class.getName()).log(Level.SEVERE, null, ex);
                throw new FunctionException(operatorSymbol() + ": cannot open input file: " + new File(((AtomString)cdr).getValue()).getAbsolutePath());
            }
        }else{
            throw new FunctionException(operatorSymbol() + ": expects argument of type <path or string>; given " + cdr);
        }
    }

    @Override
    public String operatorSymbol() {
        return "open-input-file";
    }
}
