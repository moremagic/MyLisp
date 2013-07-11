/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.HashMap;
import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.Cell;
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
            new CarFunction(),
            new CdrFunction(),
            new AndFunction(),
            new OrFunction(),
            new NotFunction(),
            new AddFunction(),
            new SubFunction(),
            new QuoteFunction(),
            new ConsFunction(),
            new DefineFunction(),
            new IsNumber(),
            new IsNull(),
            new PairFunction(),
            new GThanFunction(),
            new IFFunction(),
            new CondFunction(),
            new MultiFunction(),
            new EqurlFunction()
        };

        for (IFunction f : funcs) {
            funcMap.put(f.functionSymbol(), f);
        }
    }

    public Sexp exec(Sexp func, Cell cell, Map<String, Sexp> env) throws FunctionException {
        //各ファンクション内でApplyすることで、遅延評価を実現します
        if (func instanceof Cell && ((Cell)func).getCar().toString().equals("lambda")) {
            Cell lambda = (Cell) func;
            Sexp[] keys = ((Cell) lambda.getCdr()[0]).getSexps();
            Sexp[] value = cell.getCdr();
            
            Map<String, Sexp> cpEnv = new HashMap<String, Sexp>(env);
            for (int i = 0; i < keys.length; i++) {
                cpEnv.put(keys[i].toString(), MyLisp.apply(value[i], env));
            }

            return MyLisp.eval(lambda.getCdr()[1], cpEnv);
        } else if (cell.getCar().toString().equals("lambda")) {
            return MyLisp.lambdaApplys(cell, env);
        } else if (funcMap.containsKey(func.toString())) {
            return funcMap.get(func.toString()).eval(cell, env);
        } else {
            throw new FunctionException("reference to undefined identifier:" + cell.getCar());
        }
    }
    
    

    
    
}
