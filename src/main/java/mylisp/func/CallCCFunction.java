package mylisp.func;

import mylisp.core.*;

import java.util.Map;

/**
 * call/cc class
 * TODO: call/cc 実装途中です
 * 
 * @author moremagic
 */
public class CallCCFunction extends AbstractOperator implements SpecialOperator{
    
    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws MyLispException {
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
