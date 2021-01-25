/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import mylisp.core.Atom;
import mylisp.core.AtomSymbol;
import mylisp.core.IPair;
import mylisp.core.Sexp;
import mylisp.core.FunctionController;
import mylisp.core.TailCallOperator;
import mylisp.func.FunctionException;

/**
 *
 * @author moremagic
 */
public class MyLisp {

    private Map<AtomSymbol, Sexp> env = new HashMap<AtomSymbol, Sexp>();

    public MyLisp() {
        try {
            //組み込み関数実行部
            callEvalFile(new File(getClass().getResource("/embedded/mylisp.ss").toURI()));
            callEvalFile(new File(getClass().getResource("/embedded/r5rs_test.ss").toURI()));

            callEvalFile(new File(getClass().getResource("/embedded/newfile").toURI()));
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
     * @param file 
     */
    final private void callEvalFile(File file) {
        try {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                StringBuilder sb = new StringBuilder();

                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }

                evals(sb.toString());
            } finally {
                if (br != null) {
                    br.close();
                }
            }
        } catch (FunctionException ex) {
            Logger.getLogger(MyLisp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MyLispPerser.ParseException ex) {
            Logger.getLogger(MyLisp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MyLisp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void callREPL() {
        try {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(System.in));

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
            } finally {
                if (br != null) {
                    br.close();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(MyLisp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param sexp
     * @return
     * @throws FunctionException
     */
    private void evals(String sexps) throws FunctionException, MyLispPerser.ParseException {
        for (Sexp sexp : MyLispPerser.parses(sexps)) {
            try {
                System.out.println(">> " + eval(sexp));
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
    }

    /**
     * @param sexp
     * @return
     * @throws FunctionException
     */
    private Sexp eval(Sexp sexp) throws FunctionException {
        System.out.println("[eval] " + sexp.toString());

        //eval実行 ＋ 末尾再帰実行
        Sexp ret = MyLisp.eval(sexp, env);
        ret = TailCallOperator.evalTailCall(ret, env);
        
        return ret;
    }
    
    public static Sexp evals(Sexp sexp, Map<AtomSymbol, Sexp> env) throws FunctionException{
        Sexp ret;
        if(sexp instanceof IPair){
            IPair pair = (IPair)sexp;

            if( pair.getCdr() == Atom.NIL ){
                //末尾再帰最適化
                ret = TailCallOperator.reserveTailCall(pair.getCar(), env);
            }else{
                MyLisp.eval(pair.getCar(), env);
                ret = MyLisp.evals(pair.getCdr(), env);
            }
        }else{
            ret = MyLisp.apply(sexp, env);
        }
        return ret;
    }
    
    /**
     * Cdr Apply は 遅延評価を実現するため各ファンクション内で実施する
     *
     * @param sexp
     * @param env
     * @return
     * @throws FunctionException
     */
    private static long evalStackCnt = 0;
    public static Sexp eval(Sexp sexp, Map<AtomSymbol, Sexp> env) throws FunctionException {    
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

    public static void tranporin(Sexp sexp, Map<AtomSymbol, Sexp> env) throws FunctionException {
        System.out.println("\t" + evalStackCnt + "  " + env.toString() + " " + sexp);
    }

    public static Sexp apply(Sexp sexp, Map<AtomSymbol, Sexp> env) throws FunctionException {
        Sexp ret = sexp;
        
        sexp = (sexp instanceof IPair && ((IPair)sexp).getCdr() == Atom.NIL)?((IPair)sexp).getCar():sexp;
        if (sexp instanceof AtomSymbol && env.containsKey((AtomSymbol) sexp)) {
            ret = env.get((AtomSymbol) sexp);
        } else if (sexp instanceof IPair && ((IPair) sexp).getList().length == 1 && ((IPair) sexp).getList()[0] instanceof AtomSymbol) {
            AtomSymbol buf = (AtomSymbol) ((IPair) sexp).getList()[0];
            if(env.containsKey(buf)){
                ret = env.get(buf);
            }
        } else if (sexp instanceof IPair) {
            ret = eval((IPair) sexp, env);
        }

        return ret;
    }
}
