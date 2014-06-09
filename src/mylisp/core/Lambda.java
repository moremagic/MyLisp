package mylisp.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
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
        Sexp[] keys = ((IPair) ((IPair) getCdr()).getCar()).getList();//cdar
        Sexp[] cddr = ((IPair) ((IPair) getCdr()).getCdr()).getList();

        //全てコピー
        LambdaEnv<AtomSymbol, Sexp> mapp = new LambdaEnv<AtomSymbol, Sexp>(env);
        mapp.putAll(localEnv);
        localEnv = mapp;


        for (int i = 0; i < keys.length; i++) {
            Sexp buf = MyLisp.eval(value[i], env);
            ((LambdaEnv)localEnv).lambdaMap.put((AtomSymbol) keys[i], buf);
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
        localEnv = env;
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
    public String toString() {
        return consCell.toString();
    }
    
    public class LambdaEnv<K, V> implements Map<K, V> {
        private Map<K, V> pearentMap;
        private HashMap<K, V> lambdaMap;

        public LambdaEnv(Map<K, V> pearentMap) {
            this.pearentMap = (pearentMap instanceof LambdaEnv)?((LambdaEnv)pearentMap).getParentMap():pearentMap;
            this.lambdaMap = new HashMap<K, V>();
        }
        
        private Map<K, V> getParentMap(){
            return (pearentMap instanceof LambdaEnv)?((LambdaEnv)pearentMap).getParentMap():pearentMap;
        }

        @Override
        public int size() {
            return pearentMap.size() + lambdaMap.size();
        }

        @Override
        public boolean isEmpty() {
            return pearentMap.isEmpty() && lambdaMap.isEmpty();
        }

        @Override
        public boolean containsKey(Object key) {
            return pearentMap.containsKey(key) || lambdaMap.containsKey(key);
        }

        @Override
        public boolean containsValue(Object value) {
            return pearentMap.containsValue(value) || lambdaMap.containsValue(value);
        }

        @Override
        public V get(Object key) {
            return (lambdaMap.containsKey(key))?lambdaMap.get(key) : pearentMap.get(key);
        }

        @Override
        public V put(K key, V value) {
            return (pearentMap.containsKey(key))?pearentMap.put(key, value):lambdaMap.put(key, value);
        }

        @Override
        public V remove(Object key) {
            return lambdaMap.remove(key);
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> m) {
            Iterator<? extends K> it = m.keySet().iterator();
            while(it.hasNext()){
                K key = it.next();
                this.put(key, m.get(key));
            }
        }

        @Override
        public void clear() {
            lambdaMap.clear();
        }

        @Override
        public Set<K> keySet() {
            Set<K> ret = new HashSet<K>( pearentMap.keySet() );
            ret.addAll(lambdaMap.keySet());
            
            return ret;
        }

        @Override
        public Collection<V> values() {
            Collection<V> ret = pearentMap.values();
            ret.addAll(lambdaMap.values());
            
            return ret;
        }

        @Override
        public Set<Entry<K, V>> entrySet() {
            Set<Entry<K, V>> ret = new HashSet<Entry<K, V>>( pearentMap.entrySet() );
            ret.addAll(lambdaMap.entrySet());
            
            return ret;
        }
    }
}
