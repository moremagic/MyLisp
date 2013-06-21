/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp;

import java.util.HashMap;
import java.util.Map;
import mylisp.core.Atom;
import mylisp.core.Cell;
import mylisp.core.Lambda;
import mylisp.core.Sexp;
import mylisp.func.FunctionController;
import mylisp.func.FunctionException;

/**
 * Application Top Class
 *
 * @author mitsu
 */
public class MyLisp {
    private Map<String, Sexp> env = new HashMap<String, Sexp>();

    public MyLisp() {
    }

    public static void main(String[] args) {
        MyLisp lisp = new MyLisp();
        try {

            {//(1)
                Cell cell = new Cell(Atom.newAtom(1));
                System.out.println(lisp.eval(cell));
            }
            {//(1 2)
                Cell cell = new Cell(Atom.newAtom(1), Atom.newAtom(2));
                System.out.println(lisp.eval(cell));
            }
            {//(+ 1 2)
                Cell cell = new Cell(Atom.newAtom("+"), Atom.newAtom(1), Atom.newAtom(2));
                System.out.println(lisp.eval(cell));
            }
            {//(cons dog (cat tree))
                Cell cell = new Cell(Atom.newAtom("cons"), Atom.newAtom("dog"), new Cell(Atom.newAtom("cat"), Atom.newAtom("tree")));
                System.out.println(lisp.eval(cell));
            }
            {//(cons dog cat tree)
                try {
                    Cell cell = new Cell(Atom.newAtom("cons"), Atom.newAtom("dog"), Atom.newAtom("cat"), Atom.newAtom("tree"));
                    System.out.println(lisp.eval(cell));
                } catch (Exception err) {
                    System.out.println(err.getMessage() + "    OK!");
                }
            }
            {//(quote (+ 1 2))
                Cell cell = new Cell(Atom.newAtom("quote"), new Cell(Atom.newAtom("+"), Atom.newAtom(1), Atom.newAtom(2)));
                System.out.println(lisp.eval(cell));
            }
            {//(+ 1 2 3 4)
                Cell cell = new Cell(Atom.newAtom("+"), Atom.newAtom(1), Atom.newAtom(2), Atom.newAtom(3), Atom.newAtom(4));
                System.out.println(lisp.eval(cell));
            }
            {//(+ 1 2 3 4 (+ 1 2) (+ 1 1))
                Cell cell = new Cell(Atom.newAtom("+"), Atom.newAtom(1), Atom.newAtom(2), Atom.newAtom(3), Atom.newAtom(4), new Cell(Atom.newAtom("+"), Atom.newAtom(1), Atom.newAtom(2)), new Cell(Atom.newAtom("+"), Atom.newAtom(1), Atom.newAtom(1)));
                System.out.println(lisp.eval(cell));
            }
            {//(+ (quote (+ 1 2)) (+ 1 1))
                try {
                    Cell cell = new Cell(Atom.newAtom("+"), new Cell(Atom.newAtom("quote"), new Cell(Atom.newAtom("+"), Atom.newAtom(1), Atom.newAtom(2))), new Cell(Atom.newAtom("+"), Atom.newAtom(1), Atom.newAtom(1)));
                    System.out.println(lisp.eval(cell));
                } catch (Exception err) {
                    System.out.println(err.getMessage() + "  OK!");
                }
            }
            {//(+ 1 2 3 4 (+ 1 2 (+ 1 1)))
                Cell cell = new Cell(Atom.newAtom("+"), Atom.newAtom(1), Atom.newAtom(2), Atom.newAtom(3), Atom.newAtom(4), new Cell(Atom.newAtom("+"), Atom.newAtom(1), Atom.newAtom(2), new Cell(Atom.newAtom("+"), Atom.newAtom(1), Atom.newAtom(1))));
                System.out.println(lisp.eval(cell));
            }
            {//(number? 1)
                Cell cell = new Cell(Atom.newAtom("number?"), Atom.newAtom(1));
                System.out.println(lisp.eval(cell));
            }
            {//(number? aaa)
                Cell cell = new Cell(Atom.newAtom("number?"), Atom.newAtom("aaa"));
                System.out.println(lisp.eval(cell));
            }
            {//(number? aaa bbb)
                try {
                    Cell cell = new Cell(Atom.newAtom("number?"), Atom.newAtom("aaa"), Atom.newAtom("bbb"));
                    System.out.println(lisp.eval(cell));
                } catch (Exception err) {
                    System.out.println(err.getMessage() + "     OK!");
                }
            }
            {//(atom? (quote (+ 1 2)))
                Cell cell = new Cell(Atom.newAtom("atom?"), new Cell(Atom.newAtom("quote"), new Cell(Atom.newAtom("+"), Atom.newAtom(1), Atom.newAtom(2))));
                System.out.println(lisp.eval(cell));
            }
            {//(atom? 1)
                Cell cell = new Cell(Atom.newAtom("atom?"), Atom.newAtom(1));
                System.out.println(lisp.eval(cell));
            }
            {//(atom? aaa)
                Cell cell = new Cell(Atom.newAtom("atom?"), Atom.newAtom("aaa"));
                System.out.println(lisp.eval(cell));
            }
            {//(atom? #f)
                Cell cell = new Cell(Atom.newAtom("atom?"), Atom.newAtom("#f"));
                System.out.println(lisp.eval(cell));
            }
            {//(atom? aaa bbb)
                try {
                    Cell cell = new Cell(Atom.newAtom("atom?"), Atom.newAtom("aaa"), Atom.newAtom("bbb"));
                    System.out.println(lisp.eval(cell));
                } catch (Exception err) {
                    System.out.println(err.getMessage() + "    OK!");
                }
            }
            {//(define a (+ 100 20)) (+ a 200)
                Cell cell = new Cell(Atom.newAtom("define"), Atom.newAtom("a"), new Cell(Atom.newAtom("+"), Atom.newAtom(100), Atom.newAtom(20)));
                Cell cell2 = new Cell(Atom.newAtom("+"), Atom.newAtom("a"), Atom.newAtom(200));

                System.out.println(lisp.eval(cell));
                System.out.println(lisp.eval(cell2));
            }
            {//(define sub1 (lambda (n) (+ n 1))) (sub1 200)
                Cell cell = new Cell(Atom.newAtom("define"), Atom.newAtom("sub1"), new Cell(Atom.newAtom("lambda"), new Cell(Atom.newAtom("n")), new Cell(Atom.newAtom("+"), Atom.newAtom("n"), Atom.newAtom(1))));
                Cell cell2 = new Cell(Atom.newAtom("sub1"), Atom.newAtom(200));

                System.out.println(lisp.eval(cell));
                System.out.println(lisp.eval(cell2));
            }
            {//(define sub1 (n) (+ n 1)) (sub1 200)
                Cell cell = new Cell(Atom.newAtom("define"), Atom.newAtom("sub1"), new Cell(Atom.newAtom("n")), new Cell(Atom.newAtom("+"), Atom.newAtom("n"), Atom.newAtom(1)));
                Cell cell2 = new Cell(Atom.newAtom("sub1"), Atom.newAtom(200));

                System.out.println(lisp.eval(cell));
                System.out.println(lisp.eval(cell2));
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
    public Sexp eval(Sexp sexp) throws FunctionException {
        System.out.println("[eval] " + sexp.toString());
        return MyLisp.eval(sexp, env);
    }

    public static Sexp eval(Sexp sexp, Map<String, Sexp> env) throws FunctionException {
        Sexp ret;
        if (sexp instanceof Cell) {
            Cell cell = (Cell) sexp;
            Sexp car = apply(cell.getCar(), env);

            if(car instanceof Lambda){
                Lambda lambda = (Lambda)car;
                Sexp[] keys = ((Cell)lambda.getCdr()[0]).getSexps();
                Sexp[] value = cell.getCdr();
                
                Map<String, Sexp> cpEnv = new HashMap<String, Sexp>(env);
                for(int i = 0 ; i < keys.length ; i++){
                    cpEnv.put(keys[i].toString(), value[i]);
                }
                
                ret = eval(lambda.getCdr()[1], cpEnv);
            }else{
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
                ret = env.get(sexp.toString());
            } else {
                ret = sexp;
            }
        }

        return ret;
    }
}
