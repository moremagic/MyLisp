package mylisp;

import mylisp.core.*;
import mylisp.proc.CarProcedure;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyLispTest {

    @Test
    void apply() throws Atom.AtomException, Procedure.ProcedureException {

        Procedure proc = new CarProcedure();

        Sexp args = new ConsCell(Atom.newAtom(1), new ConsCell(Atom.newAtom(2), new ConsCell(Atom.newAtom(3), AtomNil.INSTANCE)));
        Sexp expect = Atom.newAtom(1);

        Sexp ret = new MyLisp().apply(proc, args);
        assertEquals(expect, ret);
    }
}
