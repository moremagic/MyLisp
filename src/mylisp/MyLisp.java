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
import mylisp.core.Cell;
import mylisp.core.Sexp;
import mylisp.func.FunctionController;
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
    private Map<String, Sexp> env = new HashMap<String, Sexp>();

    public MyLisp() {
    }

    public static void main(String[] args) {
        MyLisp lisp = new MyLisp();
        if(args.length == 0){
            lisp.callREPL();
        }else{
            File f = new File(args[0]);
            if(f.exists()){
                lisp.callEvalFile(f);
                lisp.callREPL();
            }else{
                printUsage();
            }
        }
    }

    public static void printUsage(){
        System.out.println("usage: file");
    }
    
    public void callEvalFile(File file) {
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
            try{
                System.out.println(">> " + eval(sexp));
            }catch(Exception err){
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

    public static Sexp eval(Sexp sexp, Map<String, Sexp> env) throws FunctionException {
        Sexp ret;
        if (sexp instanceof Cell) {
            Cell cell = (Cell) sexp;
            Sexp car = apply(cell.getCar(), env);

            //Cdr Apply は 遅延評価を実現するため各ファンクション内で実施する
            ret = FunctionController.getInstance().exec(car, cell, env);
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

    public static Sexp apply(Sexp sexp, Map<String, Sexp> env) throws FunctionException {
        Sexp ret;
        if (sexp instanceof Cell) {
            ret = eval((Cell)sexp, env);
        } else {
            if (env.containsKey(sexp.toString())) {
                ret = env.get(sexp.toString());
            } else {
                ret = sexp;
            }
        }
        return ret;
    }
    
    /**
     * Lambda Apply
     * @param sexp
     * @param env
     * @return 
     */
    public static Sexp lambdaApplys(Sexp sexp, Map<String, Sexp> env){
        if(sexp instanceof Cell){
            Sexp[] sexps = ((Cell)sexp).getSexps();
            Sexp[] bufs = new Sexp[sexps.length];
            for(int i = 0 ; i < sexps.length ; i++){
                bufs[i] = lambdaApplys(sexps[i], env);
            }
            return new Cell(bufs);
        }else{
            if( env.containsKey(sexp.toString()) ){
                return env.get(sexp.toString());
            }else{
                return sexp;
            }
        }
    }
}
