package mylisp;

import mylisp.core.*;
import mylisp.func.*;
import mylisp.proc.CarProcedure;
import mylisp.proc.CdrProcedure;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author moremagic
 */
public class MyLisp {

    private final Map<AtomSymbol, Sexp> env = new HashMap<>();

    public MyLisp() {
        try {
            //組み込み関数実行部
            callEvalFile(new File(getClass().getResource("/embedded/mylisp.ss").toURI()));
        } catch (Exception ex) {
            Logger.getLogger(MyLisp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        MyLisp lisp = new MyLisp();
        if (args.length == 0) {
            lisp.callREPL();
        } else {
            File f = new File(args[0]);
            if (f.exists()) {
                lisp.callEvalFile(f);
                lisp.callREPL();
            } else {
                printUsage();
            }
        }
    }

    public static void printUsage() {
        System.out.println("usage: file");
    }

    /**
     * TODO:  最終的に import的なLispFunction化したい
     *
     * @param file lispファイル
     */
    private void callEvalFile(File file) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            evals(sb.toString());
        } catch (MyLispException | IOException ex) {
            Logger.getLogger(MyLisp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void callREPL() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            String line;
            System.out.print("MyLisp > ");
            while ((line = br.readLine()) != null) {
                try {
                    evals(line);
                } catch (Exception ex) {
                    Logger.getLogger(MyLisp.class.getName()).log(Level.SEVERE, null, ex);
                }

                System.out.print("MyLisp > ");
            }
        } catch (IOException ex) {
            Logger.getLogger(MyLisp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param sexps 評価対象S式
     * @throws MyLispException 評価に失敗した場合
     */
    private void evals(String sexps) throws MyLispException {
        for (Sexp sexp : MyLispParser.parses(sexps)) {
            try {
                System.out.println(">> " + eval(sexp));
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
    }

    /**
     * @param sexp 評価対象S式
     * @return 評価後のS式
     * @throws MyLispException 評価失敗
     */
    private Sexp eval(Sexp sexp) throws MyLispException {
        //eval実行 ＋ 末尾再帰実行
        Sexp ret = MyLisp.eval(sexp, env);
        ret = TailCallOperator.evalTailCall(ret, env);

        return ret;
    }

    /**
     * @param sexp 評価対象S式（複数）
     * @param env  環境
     * @return 評価後S式
     * @throws MyLispException 評価失敗
     */
    public static Sexp evals(Sexp sexp, Map<AtomSymbol, Sexp> env) throws MyLispException {
        Sexp ret;
        if (sexp instanceof IPair) {
            IPair pair = (IPair) sexp;

            if (pair.getCdr() == AtomNil.INSTANCE) {
                //末尾再帰最適化
                ret = TailCallOperator.reserveTailCall(pair.getCar(), env);
            } else {
                MyLisp.eval(pair.getCar(), env);
                ret = MyLisp.evals(pair.getCdr(), env);
            }
        } else {
            ret = MyLisp.apply(sexp, env);
        }
        return ret;
    }


    private static long evalStackCnt = 0;

    /**
     * Cdr Apply は 遅延評価を実現するため各ファンクション内で実施する
     * TODO: evalは全体を評価するだけに作り替える
     *
     * @param sexp 評価対象S式
     * @param env  環境
     * @return 評価後S式
     * @throws MyLispException 評価失敗
     */
    public static Sexp eval(Sexp sexp, Map<AtomSymbol, Sexp> env) throws MyLispException {
        evalStackCnt++;
//        {//debug-print
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < evalStackCnt; i++) {
//                sb.append(" ");
//            }
//            System.out.println(sb.append(sexp).toString());
//        }

        Sexp ret;
        if (sexp instanceof IPair) {
            IPair pair = (IPair) sexp;
            ret = FunctionController.getInstance().exec(pair, env);
        } else {
            ret = apply(sexp, env);
        }

        evalStackCnt--;
        return ret;
    }

    public static void tranporin(Sexp sexp, Map<AtomSymbol, Sexp> env) throws MyLispException {
        System.out.println("\t" + evalStackCnt + "  " + env.toString() + " " + sexp);
    }

    /**
     * TODO: Apply の引数を使用に合わせ、Apply で スペシャルフォームを実施するように作り替える
     * <p>
     * (apply proc arg1 . . . args) 手続き
     * proc は手続きでなければならず，args はリストでなければな
     * らない。proc を，リスト (append (list arg1 . . . ) args)
     * の各要素を各実引数として呼び出す。
     * (apply + (list 3 4)) =⇒ 7
     * (define compose
     * (lambda (f g)
     * (lambda args
     * (f (apply g args)))))
     * ((compose sqrt *) 12 75) =⇒ 30
     *
     * @param sexp
     * @param env
     * @return
     * @throws MyLispException
     */
    public static Sexp apply(Sexp sexp, Map<AtomSymbol, Sexp> env) throws MyLispException {
        Sexp ret = sexp;

        //TODO: この辺でやっていることがevalの仕事
        sexp = (sexp instanceof IPair && ((IPair) sexp).getCdr() == AtomNil.INSTANCE) ? ((IPair) sexp).getCar() : sexp;
        if (sexp instanceof AtomSymbol && env.containsKey(sexp)) {
            ret = env.get(sexp);
        } else if (sexp instanceof IPair && sexp.getList().length == 1 && sexp.getList()[0] instanceof AtomSymbol) {
            AtomSymbol buf = (AtomSymbol) sexp.getList()[0];
            if (env.containsKey(buf)) {
                ret = env.get(buf);
            }
        } else if (sexp instanceof IPair) {
            ret = eval(sexp, env);
        }

        return ret;
    }


    /**
     * eval
     * 参考；https://sicp.iijlab.net/fulltext/x411.html
     * <p>
     * 基本式
     * • 数値のような自己評価式に対してevalは式それ自身を返す.
     * • evalは値を得るため, 環境で変数を探す必要がある.
     * <p>
     * 特殊形式
     * • クォート式に対して, evalはクォートされている式を返す.
     * • 変数への代入(または変数の定義)は変数に対応づける新しい値を計算するため, evalを再帰的に呼び出す必要がある. 変数の束縛を修正し(または作り出し)て環境を修正する.
     * • if式は, 述語が真なら帰結式を評価し, そうでなければ代替式を評価するよう, 要素式の特別な処理を必要とする.
     * • lambda式はlambda式が指定したパラメタと本体を, 評価の環境とともに詰め合せ, 作用可能な手続きへ変換する必要がある.
     * • begin式は, 要素式の並びを現れる順に評価する必要がある.
     * • condによる場合分けはif式の入れ子に変換してから評価する.
     * <p>
     * TODO: SICP 問題4.3(p221) に対応する
     *
     * @param sexp 評価する式
     * @param env  環境
     * @return 評価された式
     * @throws EvalException 評価時にエラーとなった場合
     */
    public Sexp eval2(Sexp sexp, Map<AtomSymbol, Sexp> env) throws Procedure.ProcedureException, EvalException {

        //self-evaluating? : 自己評価式（単独で存在しうるAtom）かどうか
        //if ((sexp instanceof IPair) && !sexp.isPair()) return eval2(((IPair) sexp).getCar(), env);
        if ((sexp instanceof Atom) && !(sexp instanceof AtomSymbol) && !(sexp instanceof AtomPort)) return sexp;

        // valiable? : Symbol だった場合環境から値を探す
        if (sexp instanceof AtomSymbol && env.containsKey(sexp)) return env.get(sexp);

        //quoted? : quote かどうか
        // TODO: quote を実装する
        if (sexp instanceof IPair && sexp.isPair() && ((IPair) sexp).getCar().toString().equals("quote")) {
            //cadr を返却する
            return eval2(((IPair) ((IPair) sexp).getCdr()).getCar(), env);
        }

        //assignment? : set! かどうか
        // TODO: set! を実装する

        //difinition? : define かどうか
        // TODO: define を実装する

        //if? : if かどうか
        //TODO if を実装する

        //lambbda? : lambda かどうか
        //TODO lambda を実装する

        //begin? : begin かどうか
        //TODO begin を実装する

        //cond? : cond かどうか
        //TODO cond を実装する


        if(sexp instanceof AtomSymbol)return sexp;

        //application? : pairだったら
        if (sexp instanceof IPair && sexp.isPair()) {

            Sexp car = eval2(((IPair) sexp).getCar(), env);
            return apply((AtomSymbol) car,
                    listOfValue(((IPair) sexp).getCdr(), env));
        }

        throw new EvalException(String.format("Unknown expression type -- EVAL: %s", sexp));
    }


    public Sexp listOfValue(Sexp sexp, Map<AtomSymbol, Sexp> env) throws Procedure.ProcedureException, EvalException {
        if (sexp instanceof IPair) {
            IPair pair = (IPair) sexp;

            Sexp car = eval2(pair.getCar(), env);
            return new ConsCell(car, listOfValue(pair.getCdr(), env));
        }
        return sexp;
    }

    public Sexp apply(AtomSymbol proc, Sexp args) throws Procedure.ProcedureException {
        return getSpecialform(proc).apply(args);
    }

    private static Procedure getSpecialform(AtomSymbol symbol) throws Procedure.ProcedureException {
        Procedure[] procArray = {
                new CarProcedure(),
                new CdrProcedure()
        };
        for (Procedure proc : procArray) {
            if (symbol.toString().equals(proc.procedureSymbol())) return proc;
        }
        throw new Procedure.ProcedureException(String.format("Unknown procedure type -- APPLY: %s", symbol));
    }


    public static class EvalException extends MyLispException {
        public EvalException(String message) {
            super(message);
        }
    }
}
