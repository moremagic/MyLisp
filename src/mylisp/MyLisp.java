/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp;

import java.util.HashMap;
import java.util.Map;
import mylisp.core.Cell;
import mylisp.core.Lambda;
import mylisp.core.Sexp;
import mylisp.func.FunctionController;
import mylisp.func.FunctionException;

/**
 *
 * @author moremagic
 */
public class MyLisp {
    /** 末尾最適化フラグ 末尾最適化が可能なFunctionでは再評価のためtailCallフラグをTrueにする **/
    public static boolean tailCall = false;
    private Map<String, Sexp> env = new HashMap<String, Sexp>();

    public MyLisp() {
    }

    public static void main(String[] args) {
        String[] code = {
            //"(1 2)",;err
            "(+ 1 2.012)",
            "(cons dog (cat tree))",
            //"(cons dog cat tree)",//err
            "(quote (+ 1 2))",
            "(+ 1 2 3 4.10)",
            "(+ 1 2 3 4 (+ 1 2) (+ 1 1))",
            //"(+ (quote (+ 1 2)) (+ 1 1))",//err
            "(+ 1 2 3 4 (+ 1 2 (+ 1 1)))",
            "(number? 1)",
            //"(number? aaa)",err
            //"(number? aaa bbb)",//err
            "(define a (+ 100 20)) (+ a 200)",
            "(define sub1 (lambda (n) (+ n 1))) (sub1 200)",
            "(define sub1 (n) (+ n 1)) (sub1 200)",
            "(null? (quote ()))",
            "(null? (quote aaa))",
            "(null? 0)",
            "(and #f #t (null? ()))",
            "(or #t (+ a b))",
            "(not (+ 1 2))",
            "(pair? (quote (1 2 3)))",
            "(> 2.12 2.1)",
            "(- 10 0.0001)",
            "(define < (lambda (a m) (> m a)))",
            "(define factorial (lambda (n m) (if (< n 1) m (factorial (- n 1) (* n m)))))",
            "(factorial 2800 1)",
//            "(define factorial2 (lambda (n) (if (< n 1) 1 (* n (factorial2 (- n 1))))))",
//            "(factorial2 2000)", //            "(* 10 20)",
//            "((lambda (n) (+ 1 n)) 1)",
//            "(define atom? (lambda (x) (and (not (pair? x)) (not (null? x)))))",
//            "(define (lat? n) (cond ((atom? n) true)  ((null? n) true)  (else (and (atom? (car n))  (lat? (cdr n))))))",
//            "(lat? (quote (1 (3 4))))"
        };

        MyLisp lisp = new MyLisp();
        try {
            for (String s : code) {
                System.out.println(s);
                lisp.evals(s);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    /**
     * @param sexp
     * @return
     * @throws FunctionException
     */
    public void evals(String sexps) throws FunctionException, MyLispPerser.ParseException {
        for (Sexp sexp : MyLispPerser.parses(sexps)) {
            System.out.println(">> " + eval(sexp));
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
            Cell cell = (Cell) sexp;
            Sexp car = cell.getCar();
            Sexp[] cdr = cell.getCdr();

            if (car.toString().equals("lambda")) {
                ret = eval(new Lambda(car, cdr), env);
            } else {
                ret = eval(new Cell(car, cdr), env);
            }
        } else {
            if (env.containsKey(sexp.toString())) {
                ret = env.get(sexp.toString());
            } else {
                ret = sexp;
            }
        }

        return ret;
    }
}
