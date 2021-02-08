package mylisp.core;

/**
 * S exp Interface
 *
 * @author moremagic
 */
public interface Sexp {
    //TODO: ListではなくArray
    public Sexp[] getList();

    @Override
    public abstract String toString();
}
