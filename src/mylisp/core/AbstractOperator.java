/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp.core;

import mylisp.func.FunctionException;


/**
 * Abstract Operator class
 * @author moremagic
 */
public abstract class AbstractOperator implements Operator {
    public void checkArgmunet(IPair cons, int argsLength) throws FunctionException{
        if (cons.getCdr().getList().length != argsLength) {
            StringBuilder sb = new StringBuilder();
            sb.append(operatorSymbol()).append(": expects ").append(argsLength).append(" argument, given ").append(cons.getCdr().getList().length);
            throw new FunctionException(sb.toString());
        }
    }
}
