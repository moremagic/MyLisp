package mylisp.core;

import java.util.HashMap;
import java.util.Map;

import mylisp.func.*;

/**
 * function callable class
 *
 * @author moremagic
 */
public class FunctionController {

    private static final FunctionController instance = new FunctionController();
    private final Map<String, Operator> funcMap = new HashMap<>();

    public static FunctionController getInstance() {
        return instance;
    }

    /**
     * 拡張用Operator登録
     *
     * @param oper 拡張オペレーター
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
                new IsZero(),
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

    /**
     *
     * @param pair
     * @param env
     * @return
     * @throws MyLispException
     */
    public Sexp apply(IPair pair, Map<AtomSymbol, Sexp> env) throws MyLispException {
        //各ファンクション内でApplyすることで、遅延評価を実現します
        Sexp car = pair.getCar();
        if (car == null || car == AtomNil.INSTANCE) {
            return AtomNil.INSTANCE;
        } else if (car instanceof Lambda) {
            return ((Lambda) car).lambdaEvals(env, pair.getCdr().getList());
        } else if (car instanceof IPair && funcMap.containsKey(((IPair) car).getCar().toString())) {
            return apply((IPair) car, env);
        } else if (funcMap.containsKey(car.toString())) {
            //スペシャルフォーム実行
            Operator op = funcMap.get(car.toString());
            return op.apply(pair, env);
        } else if (car instanceof Atom) {
            return car;
        } else {
            throw new AbstractOperator.FunctionException("reference to undefined identifier:" + pair.toString());
        }
    }

}
