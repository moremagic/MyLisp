/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.core.AtomSymbol;
import mylisp.core.Cell;
import mylisp.core.Sexp;

/**
 * EnvPrintFunction class
 *
 * @author moremagic
 */
public class EnvPrintFunction implements IFunction {

    @Override
    public Sexp eval(Cell cell, Map<AtomSymbol, Sexp> env) throws FunctionException {
        System.out.println("<<env-list>>");  
        for(Map.Entry<AtomSymbol, Sexp> item: env.entrySet()){
          System.out.println("[" + item.getKey() + " : " + item.getValue() + "] , ");  
        }
        return cell;
    }

    @Override
    public String functionSymbol() {
        return "env-print";
    }
}
