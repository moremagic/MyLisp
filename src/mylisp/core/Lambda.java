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
    private Cell cell;
    private Map<AtomSymbol, Sexp> localEnv = new HashMap<AtomSymbol, Sexp>();

    public Lambda(Sexp car, Sexp[] cdr) {
        assert !car.toString().equals(LAMBDA_SYMBOL) : "not lambda";
        cell = new Cell(car, cdr);
    }

    public Lambda(Sexp... sexps) {
        cell = new Cell(sexps);
    }

    @Override
    public Sexp getCar() {
        return cell.getCar();
    }

    @Override
    public Sexp[] getCdr() {
        return cell.getCdr();
    }

    @Override
    public Sexp[] getSexps() {
        return cell.getSexps();
    }

    public Sexp lambdaEvals(Map<AtomSymbol, Sexp> env, Sexp[] value) throws FunctionException {
        Sexp[] keys = ((Cell) getCdr()[0]).getSexps();
        
        //全てコピー
        HashMap<AtomSymbol, Sexp> mapp = new HashMap<AtomSymbol, Sexp>(env);
        mapp.putAll(localEnv);
        localEnv = mapp;
        for (int i = 0; i < keys.length; i++) {
            localEnv.put((AtomSymbol) keys[i], MyLisp.apply(value[i], env));
        }

        Sexp ret = null;
        for(int i = 1 ; i < getCdr().length ; i++){
            if(i == getCdr().length-1){
                ret = TailCallOperator.resurveTailCall(getCdr()[i], localEnv);
            }else{
                ret = MyLisp.eval(getCdr()[i], localEnv);
            }
        }
        
        return ret;
    }

    public void lambdaApply(Map<AtomSymbol, Sexp> env) throws FunctionException {
        localEnv.putAll(env);
    }

    @Override
    public String toString() {
        return cell.toString();
        //return cell.toString() + "[" + localEnv.getClass().getName() + "@" + Integer.toHexString(localEnv.hashCode()) +"]";
    }

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("<#function ").append(getClass().getName()).append("@").append(Integer.toHexString(hashCode()));
//        sb.append(">");
//        return sb.toString();
//    }
}
