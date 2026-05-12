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

            replEvals(sb.toString());
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
                    replEvals(line);
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
    private void replEvals(String sexps) throws MyLispException {
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
     * Cdr Apply は 遅延評価を実現するため各ファンクション内で実施する
     * TODO: evalは全体を評価するだけに作り替える
     *
     * @param sexp 評価対象S式
     * @param env  環境
     * @return 評価後S式
     * @throws MyLispException 評価失敗
     */
    public static Sexp eval(Sexp sexp, Map<AtomSymbol, Sexp> env) throws MyLispException {
        // eval；carが iPairである限り evalを呼ぶ。そうでなければApply
        // apply; cdr が IPairである限りevalを呼ぶ。そうでなければexec
        if( sexp instanceof AtomSymbol ){
            if(env.containsKey((AtomSymbol) sexp)) {
                return env.get((AtomSymbol) sexp);
            }
        }else if( sexp instanceof IPair ) {
            return apply(new ConsCell(eval(((IPair)sexp).getCar(), env), ((IPair)sexp).getCdr()), env);
        }

        return sexp;
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
        if (sexp instanceof IPair) {
            IPair pair = (IPair) sexp;
            return FunctionController.getInstance().apply(new ConsCell(pair.getCar(), pair.getCdr()), env);
        }else{
            return eval(sexp, env);
        }
    }
}
