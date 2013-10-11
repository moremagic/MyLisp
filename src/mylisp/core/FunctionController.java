/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.core;

import java.util.HashMap;
import java.util.Map;
import mylisp.MyLisp;
import mylisp.func.AddFunction;
import mylisp.func.AndFunction;
import mylisp.func.AshFunction;
import mylisp.func.CarFunction;
import mylisp.func.CdrFunction;
import mylisp.func.CloseInputFileFunction;
import mylisp.func.CloseOutputFileFunction;
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
import mylisp.func.IsChar;
import mylisp.func.IsEOF;
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
     * 拡張用Operator登録
     *
     * @param oper
     */
    public void addOperator(Operator oper) {
        funcMap.put(oper.operatorSymbol(), oper);
    }

    private FunctionController() {
        Operator[] funcs = {
            new EnvPrintFunction(),//debug-function
            new TimeFunction(),//debug-function
            new CarFunction(),
            new CdrFunction(),
            new AndFunction(),
            new AshFunction(),
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
            new IsChar(),
            new GThanFunction(),
            new IFFunction(),
            new IsPort(),
            new IsEOF(),
            new CondFunction(),
            new EqualFunction(),
            new SetFunction(),
            new DisplayFunction(),
            new OpenInputFileFunction(),
            new CloseInputFileFunction(),
            new OpenOutputFileFunction(),
            new CloseOutputFileFunction(),
            new ReadCharFunction(),
            new LetFunction(), // new CallCCFunction(),
        };

        for (Operator f : funcs) {
            funcMap.put(f.operatorSymbol(), f);
        }
    }

    public Sexp exec(IPair pair, Map<AtomSymbol, Sexp> env) throws FunctionException {
        //各ファンクション内でApplyすることで、遅延評価を実現します
        Sexp car = MyLisp.apply(pair.getCar(), env);
        if (car == null || car == ConsCell.NIL) {
            return ConsCell.NIL;
        } else if (car instanceof Lambda) {
            return ((Lambda) car).lambdaEvals(env, pair.getCdr().getList());
        } else if (pair instanceof Lambda) {
            Lambda ll = new Lambda(pair.getCar(), pair.getCdr());
            ll.lambdaApply(env);
            return ll;
        } else if (funcMap.containsKey(car.toString())) {
            //TODO ; 将来的にはスペシャルフォーム以外のcdr applyはここで統一して行いたい。
            Operator op = funcMap.get(car.toString());
            return op.eval((ConsCell) pair, env);
        } else if (car instanceof ConsCell && funcMap.containsKey( ((ConsCell)car).getCar().toString() )) {
            return MyLisp.eval((IPair) car, env);
        } else if (pair.getCdr() == ConsCell.NIL) {
            return car;
        } else {
            throw new FunctionException("reference to undefined identifier:" + pair.toString());
        }
    }
}
