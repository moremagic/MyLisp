/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.core;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import mylisp.MyLisp;
import mylisp.func.FunctionException;

/**
 * 末尾再帰用トランポリン？関数
 *
 * @author moremagic
 */
public class TailCallOperator {
    /**
     * 末尾最適化フラグ 末尾最適化が可能なFunctionでは再評価のためtailCallフラグをTrueにする
     */
    private static boolean tailCall = false;

    /**
     * 末尾再帰が必要かどうかを判断し、 末尾再帰が必要な場合、遅延評価を行うようにする
     *
     * @param sexp
     * @param m_tailCallEnv
     * @return
     * @throws FunctionException
     */
    public static Sexp reserveTailCall(Sexp sexp, Map<AtomSymbol, Sexp> tailCallEnv) throws FunctionException {
        //末尾再帰判定
        if (!tailCall && isTailCall(sexp, tailCallEnv)) {
            TailCallOperator.tailCall = true;
            return sexp;
            
            
//System.out.println("[tail-call!]" + sexp.toString());
//            Lambda tailCallLambda = new Lambda(((IPair)sexp).getCar(), ((IPair)sexp).getCdr());
//            tailCallLambda.lambdaApply(tailCallEnv);
//            return tailCallLambda;

        } else {
            //末尾再帰しない
            return MyLisp.eval(sexp, tailCallEnv);
        }
    }

    public static Sexp evalTailCall(Sexp sexp, Map<AtomSymbol, Sexp> env) throws FunctionException {
        Sexp ret = sexp;
        while (TailCallOperator.tailCall) {
            TailCallOperator.tailCall = false;
            ret = MyLisp.eval(ret, env);
        }
        return ret;
    }

    /**
     * 末尾再帰コードかどうか判定する
     * TODO: 未完成のため常にFalseを返却するようにしています
     *
     * @param sexp
     * @return
     */
    private static boolean isTailCall(Sexp sexp, Map<AtomSymbol, Sexp> env) {
        return false;
//        return (sexp instanceof IPair && isProcess((IPair) sexp, env));
    }

    private static boolean isProcess(IPair pair, Map<AtomSymbol, Sexp> env) {
        boolean ret = false;
        try { 
            Sexp sexp = MyLisp.apply(pair.getCar(), env);
            if (sexp instanceof IPair) {
                ret = isProcess((IPair) sexp, env);
            } else {
                String buf = sexp.toString();
                ret = (buf.equals(Lambda.LAMBDA_SYMBOL) || buf.equals("if") || buf.equals("cond") ) && pair.getCdr().getList().length == 1;
//                ret = (sexp instanceof Lambda) && pair.getCdr().getList().length == 1;
            }
if(ret)System.err.println("[ " + pair.toString() + "  car: " + sexp + " ] " + ret);
        } catch (FunctionException ex) {
            ret = false;
            Logger.getLogger(TailCallOperator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
}
