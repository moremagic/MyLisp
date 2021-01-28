/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.core.AbstractOperator;
import mylisp.core.Atom;
import mylisp.core.AtomSymbol;
import mylisp.core.IPair;
import mylisp.core.Sexp;
import mylisp.core.SpecialOperator;

/**
 * call/cc class
 * ※実装途中です
 * 
 * @author moremagic
 */
public class CallCCFunction extends AbstractOperator implements SpecialOperator{
    
    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        super.checkArgument(cons, 1);

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
