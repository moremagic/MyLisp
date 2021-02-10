package mylisp;

import mylisp.core.Atom;
import mylisp.core.AtomChar;
import mylisp.core.Operator;
import mylisp.core.Sexp;
import mylisp.func.AddFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyLispTest {

    @Test
    void apply() throws Atom.AtomException {

        Operator op = new AddFunction();
        Sexp[] args = new Sexp[]{Atom.newAtom(3), AtomChar.newAtom(4)};
        Sexp expect = AtomChar.newAtom(7);

        Sexp ret = MyLisp.apply(op, args);
        assertEquals(expect, ret);
    }
}
