/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.AbstractOperator;
import mylisp.core.Atom;
import mylisp.core.AtomSymbol;
import mylisp.core.ConsCell;
import mylisp.core.IPair;
import mylisp.core.Lambda;
import mylisp.core.Sexp;
import mylisp.core.SpecialOperator;

/**
 * define Function
 *
 * @author moremagic
 */
public class DefineFunction extends AbstractOperator implements SpecialOperator {

    /**
     * TODO: シンボルの評価が行われていない
     * (define aaa シンボル)のとき
     * シンボルの評価が行われていない。
     * そのため eq?-c 周りの評価が違うものになってしまっている。
     * 
     * 正解だけど うまく動かず
     * (define test (eq?-c 'salad))
     * (define aaa 'salad)
     * (test aaa) -> #f
     * 
     * 間違いだけどうまく動く
     * (define test (eq?-c 'salad))
     * (define aaa salad) ← そもそも評価しないで登録できてしまうのがおかしい
     * (test aaa) -> #t
     * 
     * ※ 束縛 aaa が (define aaa (quote salad))  で salad になるようにしないとおかしい。
     */
    
    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        //super.checkArgmunet(cons, 2);
        Sexp ret;

        Sexp cadr = ((IPair)cons.getCdr()).getCar(); // 2
        Sexp cddr = ((IPair)cons.getCdr()).getCdr(); // 3-
        
        if(cadr.getList().length == 1 && cddr.getList().length == 1){
            cddr = (cddr instanceof IPair)?((IPair)cddr).getCar():cddr;
            if( cddr.getList()[0] instanceof AtomSymbol && env.containsKey((AtomSymbol)cddr.getList()[0]) ){
                cddr = MyLisp.eval(cddr, env);
            }
            env.put((AtomSymbol) cadr.getList()[0], cddr);
            ret = Atom.newAtom(cadr.toString().toUpperCase());
        }else{
            //Function生成時の構文糖衣
            Sexp caadr = (cadr instanceof IPair)?((IPair)cadr).getCar():cadr;
            Sexp cdadr = (cadr instanceof IPair)?((IPair)cadr).getCdr():Atom.NIL;
            env.put((AtomSymbol) caadr, new Lambda(Atom.newAtom(Lambda.LAMBDA_SYMBOL), new ConsCell(cdadr, cddr)));
            ret = Atom.newAtom(caadr.toString().toUpperCase());
        }
        
        return ret;
    }

    @Override
    public String operatorSymbol() {
        return "define";
    }
}
