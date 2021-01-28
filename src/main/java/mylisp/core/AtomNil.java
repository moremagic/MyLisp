package mylisp.core;

/**
 * nil atom class
 * //TODO インスタンスが必ず同じものになるようにしたほうが良い
 *
 * @author moremagic
 */
public class AtomNil extends Atom {
    public static final AtomNil INSTANCE = new AtomNil();

    private AtomNil() {
    }

    public static AtomNil getInstance() {
        return AtomNil.INSTANCE;
    }

    @Override
    public Object getValue() {
        return "";
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof AtomNil;
    }
}
