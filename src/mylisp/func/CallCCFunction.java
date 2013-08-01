/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.Atom;
import mylisp.core.AtomSymbol;
import mylisp.core.Cell;
import mylisp.core.Sexp;
import mylisp.core.SpecialOperator;

/**
 * call/cc class
 * ※実装途中です
 * 
 * @author moremagic
 */
public class CallCCFunction implements SpecialOperator{
    
    @Override
    public Sexp eval(Cell cell, Map<AtomSymbol, Sexp> env) throws FunctionException {
        if(cell.getCdr().length != 1){
            throw new FunctionException(operatorSymbol() + ": expects 1 argument, given " + cell.getCdr().length );
        }
        

        //TODO たぶんこんな感じで行ける
        //Lambda クラスを生成して返す。
        //評価は停止する。
        
        return Atom.newAtom(1);
    }
    
    @Override
    public String operatorSymbol() {
        return "call/cc";
    }

}
