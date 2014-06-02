package mylisp.core;

import java.util.HashMap;
import java.util.Map;
import mylisp.MyLisp;
import mylisp.func.FunctionException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Lambda
 *
 * @author moremagic
 */
public class Lambda implements IPair {
    public static final String LAMBDA_SYMBOL = "lambda";
    private ConsCell consCell;
    private Map<AtomSymbol, Sexp> localEnv = new HashMap<AtomSymbol, Sexp>();

    public Lambda(Sexp car, Sexp cdr) {
        assert !car.toString().equals(LAMBDA_SYMBOL) : "not lambda";
        consCell = new ConsCell(car, cdr);
    }

    public Sexp lambdaEvals(Map<AtomSymbol, Sexp> env, Sexp[] value) throws FunctionException {
        Sexp[] keys = ((IPair)((IPair)getCdr()).getCar()).getList();//cdar
        Sexp[] cddr = ((IPair)((IPair)getCdr()).getCdr()).getList();

        //全てコピー
        Map<AtomSymbol, Sexp> mapp = new HashMap<AtomSymbol, Sexp>(env);
        mapp.putAll(localEnv);
        localEnv = mapp;
        
        for (int i = 0; i < keys.length; i++) {
            Sexp buf = MyLisp.eval(value[i], env);
            localEnv.put((AtomSymbol) keys[i], buf);
        }

        Sexp ret = null;
        for (int i = 0; i < cddr.length; i++) {
            if (i == cddr.length - 1) {
                ret = TailCallOperator.reserveTailCall(cddr[i], localEnv);
            } else {
                ret = MyLisp.eval(cddr[i], localEnv);
            }
        }
        return ret;
    }

    public void lambdaApply(Map<AtomSymbol, Sexp> env) throws FunctionException {
        localEnv.putAll(env);
    }

    @Override
    public void setCar(Sexp car) {
        consCell.setCar(car);
    }

    @Override
    public Sexp getCar() {
        return consCell.getCar();
    }

    @Override
    public Sexp getCdr() {
        return consCell.getCdr();
    }

    @Override
    public void setCdr(Sexp cdr) {
        consCell.setCdr(cdr);
    }

    @Override
    public Sexp[] getList() {
        return consCell.getList();
    }
    
    @Override
    public String toString(){
        return consCell.toString();
    }
    
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("<#function ").append(getClass().getName()).append("@").append(Integer.toHexString(hashCode()));
//        sb.append(">");
//        return sb.toString();
//    }
}
