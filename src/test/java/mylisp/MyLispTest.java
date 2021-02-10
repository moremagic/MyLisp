package mylisp;

import mylisp.core.*;
import mylisp.func.AddFunction;
import mylisp.func.CarProcedure;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyLispTest {

    @Test
    void apply() throws Atom.AtomException {

        Procedure proc = new CarProcedure();

        Sexp args = MyLispParser.parses("(1 2 3)")[0];
        Sexp expect = MyLispParser.parses("1")[0];

        Sexp ret = MyLisp.apply(proc, args);
        assertEquals(expect, ret);
    }
}
