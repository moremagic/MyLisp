/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.Atom;
import mylisp.core.Cell;
import mylisp.core.IPair;
import mylisp.core.Lambda;
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
            if(cdrs[0] instanceof IPair){
                Sexp car = ((IPair)cdrs[0]).getCar();
                Sexp[] cdr = ((IPair)cdrs[0]).getCdr();
                
                //Function生成時の構文糖衣          
                env.put(car.toString(), new Lambda(Atom.newAtom(Lambda.LAMBDA_SYMBOL), new Sexp[]{new Cell(cdr), cdrs[1]}));
            }else{
                Sexp ss = MyLisp.apply(cdrs[1], env);
                if(ss instanceof IPair){
                    env.put(cdrs[0].toString(), (Lambda)ss);
                }else{
                    env.put(cdrs[0].toString(), ss);
                }
            }
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
