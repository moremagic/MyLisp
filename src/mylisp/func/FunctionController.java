/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.HashMap;
import java.util.Map;
import mylisp.core.Cell;
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
            new AndFunction() ,
            new OrFunction() ,
            new NotFunction() ,
            new AddFunction() ,
            new SubFunction() ,
            new QuoteFunction() ,
            new ConsFunction() ,
            new DefineFunction() ,
            new IsNumber() ,
            new IsNull() ,
            new PairFunction() ,
            new GThanFunction(),
            new IFFunction()
        };
        
        for(IFunction f: funcs){
            funcMap.put(f.functionSymbol(), f);
        }
    }

    public Sexp exec(String func, Cell cell, Map<String, Sexp> env) throws FunctionException {
        //各ファンクション内でApplyすることで、遅延評価を実現します
        if (funcMap.containsKey(func)) {
            return funcMap.get(func).eval(cell, env);
        } else if (cell instanceof Lambda) {
            //NOP
            return cell;
        } else {
            throw new FunctionException("reference to undefined identifier:" + cell.getCar());
        }
    }
}
