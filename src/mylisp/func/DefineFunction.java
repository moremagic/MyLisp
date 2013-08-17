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

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        super.checkArgmunet(cons, 2);
        Sexp ret;

        Sexp cadr = ((IPair)cons.getCdr()).getCar(); // 2
        Sexp cddr = ((IPair)cons.getCdr()).getCdr(); // 3-
        
        if(cadr.getList().length == 1){
            cddr = (cddr instanceof IPair)?((IPair)cddr).getCar():cddr;
            ret = env.put((AtomSymbol) cadr, MyLisp.apply(cddr, env));
        }else{
            //Function生成時の構文糖衣
            Sexp caadr = ((IPair)cadr).getCar();
            Sexp cdadr = ((IPair)cadr).getCdr();
            ret = env.put((AtomSymbol) caadr, new Lambda(Atom.newAtom(Lambda.LAMBDA_SYMBOL), new ConsCell(cdadr, cddr)));
        }
        
        return ret;
        
//        Sexp[] cdrs = cons.getCdr().getList();
//        Sexp ret;
//        if (cdrs[0] instanceof IPair) {
//            Sexp car = ((IPair) cdrs[0]).getCar();
//            Sexp cdr = ((IPair) cdrs[0]).getCdr();
//
//            //Function生成時の構文糖衣          
//            ret = env.put((AtomSymbol) car, new Lambda(Atom.newAtom(Lambda.LAMBDA_SYMBOL), new ConsCell(cdr, cdrs[1])));
//        } else {
//            Sexp ss = MyLisp.apply(cdrs[1], env);
//            ret = env.put((AtomSymbol) cdrs[0], ss);
//        }
//
//        return ret;
    }

    @Override
    public String operatorSymbol() {
        return "define";
    }
}
