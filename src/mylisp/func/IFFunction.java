/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.func;

import java.util.Map;
import mylisp.MyLisp;
import mylisp.core.AtomBoolean;
import mylisp.core.Cell;
import mylisp.core.Sexp;

/**
 * IF class
 * @author moremagic
 */
public class IFFunction implements IFunction{

    @Override
    public Sexp eval(Cell cell, Map<String, Sexp> env) throws FunctionException {
        if(cell.getCdr().length < 3){
            throw new FunctionException("if: bad syntax (has 1 part after keyword) in: " + cell.toString());
        } 
        
        Sexp sexp = MyLisp.apply(cell.getCdr()[0], env);
        if(sexp instanceof AtomBoolean && sexp.toString().equals("#f")){
            MyLisp.tailCall = true;
            return cell.getCdr()[2];
        }else{
            return MyLisp.eval(cell.getCdr()[1], env);
        }
    }
    
    @Override
    public String functionSymbol() {
        return "if";
    }
}
