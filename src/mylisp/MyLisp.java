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
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import mylisp.core.AtomSymbol;
import mylisp.core.IPair;
import mylisp.core.Sexp;
import mylisp.core.FunctionController;
import mylisp.func.FunctionException;

/**
 *
 * @author moremagic
 */
public class MyLisp {

    /**
     * 末尾最適化フラグ 末尾最適化が可能なFunctionでは再評価のためtailCallフラグをTrueにする *
     */
    public static boolean tailCall = false;
    private Map<AtomSymbol, Sexp> env = new HashMap<AtomSymbol, Sexp>();

    public MyLisp() {
        try {
            callEvalFile(new File(getClass().getResource("/mylisp/embedded/mylisp.ss").toURI()));
        } catch (URISyntaxException ex) {
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

    final public void callEvalFile(File file) {
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

    public void callREPL() {
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
    public void evals(String sexps) throws FunctionException, MyLispPerser.ParseException {
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
    public Sexp eval(Sexp sexp) throws FunctionException {
        System.out.println("[eval] " + sexp.toString());
        return MyLisp.eval(sexp, env);
    }

    /**
     * Cdr Apply は 遅延評価を実現するため各ファンクション内で実施する
     *
     * @param sexp
     * @param env
     * @return
     * @throws FunctionException
     */
    public static Sexp eval(Sexp sexp, Map<AtomSymbol, Sexp> env) throws FunctionException {
        Sexp ret;
        if (sexp instanceof IPair) {
            IPair pair = (IPair) sexp;
            if (pair.getSexps().length != 0) {
                Sexp car = apply(pair.getCar(), env);
                ret = FunctionController.getInstance().exec(car, pair, env);
            } else {
                ret = pair;
            }
        } else {
            ret = apply(sexp, env);
        }

        //末尾最適化のための再評価:うまく動いていない。。＞＜；
        if (tailCall) {
            tailCall = false;
            ret = eval(ret, env);
        }

        return ret;
    }

    public static Sexp apply(Sexp sexp, Map<AtomSymbol, Sexp> env) throws FunctionException {
        Sexp ret;
        if (sexp instanceof IPair) {
            ret = eval((IPair) sexp, env);
        } else if (sexp instanceof AtomSymbol && env.containsKey((AtomSymbol) sexp)) {
            ret = env.get((AtomSymbol) sexp);
        } else {
            ret = sexp;
        }
        return ret;
    }
}
