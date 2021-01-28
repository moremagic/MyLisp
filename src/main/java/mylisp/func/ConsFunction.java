/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.AbstractOperator;
import mylisp.core.AtomSymbol;
import mylisp.core.ConsCell;
import mylisp.core.IPair;
import mylisp.core.Sexp;

/**
 * cons Function
 * @author moremagic
 */
public class ConsFunction extends AbstractOperator{
    @Override
    public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException{
        super.checkArgument(cons, 2);
        
        Sexp[] list = cons.getCdr().getList();
        
        Sexp car = MyLisp.apply(list[0], env);
        if(car instanceof IPair){            
            ((IPair)car).setCdr(MyLisp.apply(list[1], env));
            return car;
        }else{
            return new ConsCell(car, MyLisp.apply(list[1], env));
        }
    }
    
    
    @Override
    public String operatorSymbol() {
        return "cons";
    }

}
