/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.Atom;
import mylisp.core.Cell;
import mylisp.core.Sexp;

/**
 * define Function
 * @author moremagic
 */
public class DefineFunction implements IFunction{
    @Override
    public Sexp eval(Cell cell, Map<String, Sexp> env) throws FunctionException{        
        Sexp[] cdrs = cell.getCdr();
        if(cdrs.length == 2){
            env.put(cdrs[0].toString(), MyLisp.apply(cdrs[1], env));
        }else if(cdrs.length == 3){
            //構文糖衣 Function の Lambda化
            env.put(cdrs[0].toString(), new Cell(Atom.newAtom("lambda"), cdrs[1], cdrs[2]));
        }else{
            throw new FunctionException("define: expects arguments");
        }
        
        return env.get(cdrs[0].toString());
    }

    @Override
    public String functionSymbol() {
        return "define";
    }
}
