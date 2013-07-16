/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.HashMap;
import java.util.Map;
import mylisp.core.Cell;
import mylisp.core.IPair;
import mylisp.core.Lambda;
import mylisp.core.Sexp;

/**
 * function callable class
 *
 * @author moremagic
 */
public class FunctionController {

    private static FunctionController instance = new FunctionController();
    private Map<String, IFunction> funcMap = new HashMap();

    public static FunctionController getInstance() {
        return instance;
    }

    private FunctionController() {
        IFunction[] funcs = {
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
            new IsNumber(),
            new IsNull(),
            new PairFunction(),
            new GThanFunction(),
            new IFFunction(),
            new CondFunction(),
            new EqualFunction(),
            new SetFunction()
        };

        for (IFunction f : funcs) {
            funcMap.put(f.functionSymbol(), f);
        }
    }

    public Sexp exec(Sexp func, IPair pair, Map<String, Sexp> env) throws FunctionException {
        //各ファンクション内でApplyすることで、遅延評価を実現します        
        if (func instanceof Lambda) {
            return ((Lambda) func).lambdaEvals(env, pair.getCdr());
        } else if (pair instanceof Lambda) {
            ((Lambda) pair).lambdaApply(env);
            return pair;
        } else if (funcMap.containsKey(func.toString()) && pair instanceof Cell) {
            return funcMap.get(func.toString()).eval((Cell) pair, env);
        } else {
            throw new FunctionException("reference to undefined identifier:" + pair.toString());
        }
    }
}
