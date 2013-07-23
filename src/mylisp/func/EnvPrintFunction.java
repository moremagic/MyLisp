/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import mylisp.core.Operator;
import java.util.Map;
import mylisp.core.AtomSymbol;
import mylisp.core.Cell;
import mylisp.core.Sexp;

/**
 * EnvPrintFunction class
 *
 * @author moremagic
 */
public class EnvPrintFunction implements Operator {

    @Override
    public Sexp eval(Cell cell, Map<AtomSymbol, Sexp> env) throws FunctionException {
        System.out.println("<<env-list>>");  
        for(Map.Entry<AtomSymbol, Sexp> item: env.entrySet()){
          System.out.println("[" + item.getKey() + " : " + item.getValue() + "] , ");  
        }
        return cell;
    }

    @Override
    public String operatorSymbol() {
        return "env-print";
    }
}
