package mylisp.core;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * S exp Interface
 *
 * @author moremagic
 */
public interface IPair extends Sexp {

    public Sexp getCar();

    public Sexp getCdr();

    public void setCar(Sexp car);

    public void setCdr(Sexp cdr);

    @Override
    public abstract String toString();
}
