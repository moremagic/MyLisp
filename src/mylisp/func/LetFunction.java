/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.core.Atom;
import mylisp.core.AtomSymbol;
import mylisp.core.ConsCell;
import mylisp.core.IPair;
import mylisp.core.Lambda;
import mylisp.core.Sexp;
import mylisp.core.SpecialOperator;
import mylisp.core.TailCallOperator;

/**
 * Let class
 *
 * @author moremagic
 */
public class LetFunction implements SpecialOperator {

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        assert (cons.getCdr().getList().length < 2) : "not let format!";
        
        Sexp env_list;
        Sexp lambda_body;

        IPair cdr = (IPair)cons.getCdr();
        if (cdr.getCar() instanceof IPair) {
            //名前なしLet
            env_list = cdr.getCar();
            lambda_body = cdr.getCdr();
        } else {
            //名前付きLet
            env_list = ((IPair)cdr.getCdr()).getCar();
            lambda_body = ((IPair)cdr.getCdr()).getCdr();
        }

        if (env_list instanceof IPair) {
            Sexp[] ll = ((IPair) env_list).getList();
            Sexp[] keys = new Sexp[ll.length];
            Sexp[] values = new Sexp[ll.length];
            for (int i = 0; i < ll.length; i++) {
                if (ll[i] instanceof IPair) {
                    IPair buf = (IPair) ll[i];
                    keys[i] = buf.getCar();
                    values[i] = buf.getCdr();
                } else {
                    throw new FunctionException("let: bad syntax in: " + cons.toString());
                }
            }

            if (cdr.getCar() instanceof IPair) {
                //名前なしLet
                Lambda lambda = new Lambda(Atom.newAtom(Lambda.LAMBDA_SYMBOL), new ConsCell(ConsCell.list2Cons(keys), new ConsCell(lambda_body, ConsCell.NIL)));

                //末尾再帰最適化
                return TailCallOperator.reserveTailCall(new ConsCell(lambda, ConsCell.list2Cons(values)), env);
            } else {
                //名前ありLet     
                Lambda lambda = new Lambda(Atom.newAtom(Lambda.LAMBDA_SYMBOL), new ConsCell(ConsCell.list2Cons(keys), new ConsCell(lambda_body, ConsCell.NIL)));
                env.put((AtomSymbol) cdr.getCar(), lambda);
                lambda.lambdaApply(env);

                //末尾再帰最適化
                return TailCallOperator.reserveTailCall(new ConsCell(cdr.getCar(), ConsCell.list2Cons(values)), env);
            }
        } else {
            throw new FunctionException("let: bad syntax in: " + cons.toString());
        }
    }

    @Override
    public String operatorSymbol() {
        return "let";
    }
}
