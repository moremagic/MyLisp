package mylisp;

import mylisp.core.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
     *
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
     *
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

    public static Sexp apply(Operator proc, Sexp... args) {
        return null;
    }
}
