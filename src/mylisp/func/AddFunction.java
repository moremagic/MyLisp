/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.math.BigDecimal;
import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.Atom;
import mylisp.core.AtomNumber;
import mylisp.core.Cell;
import mylisp.core.Sexp;

/**
 * +(Plus) Function
 * @author moremagic
 */
public class AddFunction implements IFunction{

    @Override
    public Sexp eval(Cell cell, Map<String, Sexp> env) throws FunctionException{
        Number ret = 0;
        for(Sexp s: cell.getCdr()){            
            Sexp buf = MyLisp.apply(s, env);
            
            if(buf instanceof AtomNumber){
                ret = addNumber(ret, ((AtomNumber)buf).getValue());
            }else{
                throw new FunctionException("reference to undefined identifier: " + buf.toString());
            }
        }
        
        return Atom.newAtom(ret);
    }
    
    private Number addNumber(Number a, Number b){
        Number ret;
        if(a instanceof Double && b instanceof Double){
             ret = (Double)a + (Double)b;
        }else if(a instanceof Integer && a instanceof Integer){
             ret = (Integer)a + (Integer)b;
        }else{
            ret = new BigDecimal((Double)a).add(new BigDecimal((Double)b));
        }
    
        return ret;
    }
    
}
