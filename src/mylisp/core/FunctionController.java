/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.core;

import java.util.HashMap;
import java.util.Map;
import mylisp.func.AddFunction;
import mylisp.func.AndFunction;
import mylisp.func.CarFunction;
import mylisp.func.CdrFunction;
import mylisp.func.CondFunction;
import mylisp.func.ConsFunction;
import mylisp.func.DefineFunction;
import mylisp.func.DisplayFunction;
import mylisp.func.EnvPrintFunction;
import mylisp.func.EqualFunction;
import mylisp.func.FunctionException;
import mylisp.func.GThanFunction;
import mylisp.func.IFFunction;
import mylisp.func.IsBoolean;
import mylisp.func.IsNull;
import mylisp.func.IsNumber;
import mylisp.func.IsPair;
import mylisp.func.IsPort;
import mylisp.func.IsString;
import mylisp.func.IsSymbol;
import mylisp.func.LetFunction;
import mylisp.func.MultiFunction;
import mylisp.func.NotFunction;
import mylisp.func.OpenInputFileFunction;
import mylisp.func.OpenOutputFileFunction;
import mylisp.func.OrFunction;
import mylisp.func.QuoteFunction;
import mylisp.func.ReadCharFunction;
import mylisp.func.SetFunction;
import mylisp.func.SubFunction;
import mylisp.func.TimeFunction;

/**
 * function callable class
 *
 * @author moremagic
 */
public class FunctionController {

    private static FunctionController instance = new FunctionController();
    private Map<String, Operator> funcMap = new HashMap();

    public static FunctionController getInstance() {
        return instance;
    }

    /**
     * 拡張用Function登録
     *
     * @param func
     */
    public void addFunction(Operator func) {
        funcMap.put(func.operatorSymbol(), func);
    }

    private FunctionController() {
        Operator[] funcs = {
            new EnvPrintFunction(),//debug-function
            new TimeFunction(),//debug-function
            new CarFunction(),
            new CdrFunction(),
            new AndFunction(),
            new OrFunction(),
            new NotFunction(),
            new AddFunction(),
            new SubFunction(),
            new MultiFunction(),
            new QuoteFunction(),
            new ConsFunction(),
            new DefineFunction(),
            new IsBoolean(),
            new IsSymbol(),
            new IsNull(),
            new IsPair(),
            new IsNumber(),
            new IsString(),
            new GThanFunction(),
            new IFFunction(),
            new IsPort(),
            new CondFunction(),
            new EqualFunction(),
            new SetFunction(),
            new DisplayFunction(),
            new OpenInputFileFunction(),
            new OpenOutputFileFunction(),
            new ReadCharFunction(),
            new LetFunction(), // new CallCCFunction,
        };

        for (Operator f : funcs) {
            funcMap.put(f.operatorSymbol(), f);
        }
    }

    public Sexp exec(Sexp func, IPair pair, Map<AtomSymbol, Sexp> env) throws FunctionException {
        //各ファンクション内でApplyすることで、遅延評価を実現します        
        if (func instanceof Lambda) {
            return ((Lambda) func).lambdaEvals(env, pair.getCdr());
        } else if (pair instanceof Lambda) {
            Lambda ll = new Lambda(pair.getSexps());
            ll.lambdaApply(env);
            return ll;
        } else if (pair instanceof Cell && funcMap.containsKey(func.toString())) {
            
            
//        if(sexp instanceof AtomSymbol){
//            throw new FunctionException("reference to undefined identifier: " + sexp);
//        }            
            
            
            return funcMap.get(func.toString()).eval((Cell) pair, env);
        } else {
            throw new FunctionException("reference to undefined identifier:" + pair.toString());
        }
    }
}
