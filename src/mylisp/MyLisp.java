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
            "(define < (a m) (> a m)))",
            "(define factorial (n) (if (< n 2) 1 (+ n (factorial (- n 1)))))",
            "(factorial 10)"
            //"(* 1 2)",
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
     *
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

            if (car instanceof Lambda) {
                Lambda lambda = (Lambda) car;
                Sexp[] keys = ((Cell) lambda.getCdr()[0]).getSexps();
                Sexp[] value = cell.getCdr();

                Map<String, Sexp> cpEnv = new HashMap<String, Sexp>(env);
                for (int i = 0; i < keys.length; i++) {
                    cpEnv.put(keys[i].toString(), value[i]);
                }

                ret = eval(lambda.getCdr()[1], cpEnv);
            } else {
                //Cdr Apply は 遅延評価を実現するため各ファンクション内で実施する
                ret = FunctionController.getInstance().exec(car.toString(), cell, env);
            }
        } else {
            ret = sexp;
        }
        return ret;
    }

    public static Sexp apply(Sexp sexp, Map<String, Sexp> env) throws FunctionException {
        Sexp ret;
        if (sexp instanceof Cell) {
            Cell cell = (Cell) sexp;
            Sexp car = apply(cell.getCar(), env);
            Sexp[] cdr = cell.getCdr();

            if (car.toString().equals("lambda")) {
                ret = eval(new Lambda(car, cdr), env);
            } else {
                ret = eval(new Cell(car, cdr), env);
            }
        } else {
            if (env.containsKey(sexp.toString())) {
                ret = apply(env.get(sexp.toString()), env);
            } else {
                ret = sexp;
            }
        }

        return ret;
    }
}
