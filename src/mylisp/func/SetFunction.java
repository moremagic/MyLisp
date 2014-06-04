/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.AbstractOperator;
import mylisp.core.Atom;
import mylisp.core.AtomSymbol;
import mylisp.core.IPair;
import mylisp.core.Sexp;

/**
 * set! class
 * @author moremagic
 */
public class SetFunction extends AbstractOperator{

    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
        super.checkArgmunet(cons, 2);
        
        Sexp[] cdrs = cons.getCdr().getList();
        if(env.containsKey((AtomSymbol)cdrs[0])){
            Sexp buf = MyLisp.apply(cdrs[1], env);
            env.put((AtomSymbol) cdrs[0], buf);
        }else{
            throw new FunctionException("set!: cannot set undefined identifier: " + cdrs[0].toString());
        }
        return Atom.newAtom("()");
    }
    
    @Override
    public String operatorSymbol() {
        return "set!";
    }
}
