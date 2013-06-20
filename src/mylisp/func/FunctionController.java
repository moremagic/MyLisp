/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.HashMap;
import java.util.Map;
import mylisp.core.Cell;
import mylisp.core.Sexp;

/**
 * function callable class
 * @author mitsu
 */
public class FunctionController {
    private static FunctionController instance = new FunctionController();    
    private Map<String, IFunction> funcMap = new HashMap();
    
    public static FunctionController getInstance(){
        return instance;
    }
    
    private FunctionController(){
        funcMap.put("+", new AddFunction());
        funcMap.put("quote", new QuoteFunction());
        funcMap.put("cons", new ConsFunction());
        funcMap.put("number?", new IsNumber());
        funcMap.put("atom?", new IsAtom());
    }
    
    public Sexp exec(String func, Cell cell, Map<String, Sexp> env) throws FunctionException{
        //各ファンクション内でApplyすることで、遅延評価を実現します
        if(funcMap.containsKey(func)){
            return funcMap.get(func).eval(cell, env);
        }else{
            return cell;
        }
    }
}
