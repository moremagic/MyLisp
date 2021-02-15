package mylisp.core;

/**
 * S exp Interface
 *
 * @author moremagic
 */
public interface Sexp {
    //TODO: ListではなくArray
    Sexp[] getList();

    default boolean isPair(){return false;}

    @Override
    String toString();
}
