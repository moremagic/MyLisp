/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.core;

import java.util.Map;
import mylisp.MyLisp;
import static mylisp.MyLisp.eval;
import mylisp.func.FunctionException;


/**
 * 末尾再帰用トランポリン？関数
 * @author moremagic
 */
public class TailCallOperator {
    /**
     * 末尾最適化フラグ 末尾最適化が可能なFunctionでは再評価のためtailCallフラグをTrueにする *
     */
    private static boolean tailCall = false;
    private static Map<AtomSymbol, Sexp> tailCallEnv = null;
    
    /**
     * 末尾再帰が必要かどうかを判断し、
     * 末尾再帰が必要な場合、遅延評価を行うようにする
     * 
     * @param sexp
     * @param tailCallEnv
     * @return
     * @throws FunctionException 
     */
    public static Sexp reserveTailCall(Sexp sexp, Map<AtomSymbol, Sexp> tailCallEnv) throws FunctionException{
        //末尾再帰判定
        if(isTailCall(sexp)){
            TailCallOperator.tailCall = true;
            TailCallOperator.tailCallEnv = tailCallEnv;
            return sexp;
        }else{
            //末尾再帰しない
            return MyLisp.eval(sexp, tailCallEnv);
        }
    }
    
    public static Sexp evalTailCall(Sexp sexp) throws FunctionException{
        Sexp ret = sexp;
        while(TailCallOperator.tailCall){
            TailCallOperator.tailCall = false;
            ret = eval(ret, tailCallEnv);
        }
        return ret;
    }
    
    /**
     * 末尾再帰コードかどうか判定する
     * TODO: 未完成
     * 
     * @param sexp
     * @return 
     */
    private static boolean isTailCall(Sexp sexp){
        return false;
    }
    
}
