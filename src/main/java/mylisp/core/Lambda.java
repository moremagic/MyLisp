package mylisp.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import mylisp.MyLisp;
import mylisp.func.FunctionException;

/**
 * Lambda
 *
 * @author moremagic
 */
public class Lambda implements IPair {
    public static final String LAMBDA_SYMBOL = "lambda";
    private final ConsCell consCell;
    private Map<AtomSymbol, Sexp> localEnv = new HashMap<>();

    public Lambda(Sexp car, Sexp cdr) {
        assert car.toString().equals(LAMBDA_SYMBOL) : "car for lambda pair must be 'lambda'";
        consCell = new ConsCell(car, cdr);
    }

    public Sexp lambdaEvals(Map<AtomSymbol, Sexp> env, Sexp[] value) throws FunctionException {
        Sexp[] keys = ((IPair) getCdr()).getCar().getList();//cdar
        Sexp cddr = ((IPair) getCdr()).getCdr();

        //全てコピー
        LambdaEnv<AtomSymbol, Sexp> mapp = new LambdaEnv<>(env);
        mapp.putAll(localEnv);
        localEnv = mapp;


        for (int i = 0; i < keys.length; i++) {
            Sexp buf = MyLisp.eval(value[i], env);
            ((LambdaEnv<AtomSymbol, Sexp>) localEnv).lambdaMap.put((AtomSymbol) keys[i], buf);
        }

        return MyLisp.evals(cddr, localEnv);
    }

    public void lambdaApply(Map<AtomSymbol, Sexp> env) {
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

    public static class LambdaEnv<K, V> implements Map<K, V> {
        private final Map<K, V> parentMap;
        private final HashMap<K, V> lambdaMap;

        public LambdaEnv(Map<K, V> parentMap) {
            this.parentMap = (parentMap instanceof LambdaEnv) ? ((LambdaEnv<K,V>) parentMap).getParentMap() : parentMap;
            this.lambdaMap = new HashMap<>();
        }

        private Map<K, V> getParentMap() {
            return (parentMap instanceof LambdaEnv) ? ((LambdaEnv<K,V>) parentMap).getParentMap() : parentMap;
        }

        @Override
        public int size() {
            return parentMap.size() + lambdaMap.size();
        }

        @Override
        public boolean isEmpty() {
            return parentMap.isEmpty() && lambdaMap.isEmpty();
        }

        @Override
        public boolean containsKey(Object key) {
            return parentMap.containsKey(key) || lambdaMap.containsKey(key);
        }

        @Override
        public boolean containsValue(Object value) {
            return parentMap.containsValue(value) || lambdaMap.containsValue(value);
        }

        @Override
        public V get(Object key) {
            return (lambdaMap.containsKey(key)) ? lambdaMap.get(key) : parentMap.get(key);
        }

        @Override
        public V put(K key, V value) {
            return (parentMap.containsKey(key)) ? parentMap.put(key, value) : lambdaMap.put(key, value);
        }

        @Override
        public V remove(Object key) {
            return lambdaMap.remove(key);
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> m) {
            Iterator<? extends K> it = m.keySet().iterator();
            while (it.hasNext()) {
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
            Set<K> ret = new HashSet<>(parentMap.keySet());
            ret.addAll(lambdaMap.keySet());

            return ret;
        }

        @Override
        public Collection<V> values() {
            Collection<V> ret = parentMap.values();
            ret.addAll(lambdaMap.values());

            return ret;
        }

        @Override
        public Set<Entry<K, V>> entrySet() {
            Set<Entry<K, V>> ret = new HashSet<>(parentMap.entrySet());
            ret.addAll(lambdaMap.entrySet());

            return ret;
        }
    }
}
