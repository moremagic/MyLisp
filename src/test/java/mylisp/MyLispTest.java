package mylisp;

import mylisp.core.*;
import mylisp.proc.CarProcedure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyLispTest {

    @Test
    void apply() throws Atom.AtomException, Procedure.ProcedureException {

        Procedure proc = new CarProcedure();

        Sexp args = new ConsCell(Atom.newAtom(1), new ConsCell(Atom.newAtom(2), new ConsCell(Atom.newAtom(3), AtomNil.INSTANCE)));
        Sexp expect = Atom.newAtom(1);

        Sexp ret = new MyLisp().apply((AtomSymbol) Atom.newAtom(proc.procedureSymbol()), args);
        assertEquals(expect, ret);
    }

    @ParameterizedTest
    @CsvSource({
            "(car '(1)), 1",
            "(car '(1 2 3)), 1",
            "(cdr '(1 (car '(2 3 4)))), 2",
    })
    void eval2(String value, String expect) throws Atom.AtomException, Procedure.ProcedureException, MyLisp.EvalException {
        Map<AtomSymbol, Sexp> map = new HashMap<>();
        Sexp sexp = MyLispParser.parses(value)[0];

        Sexp ret = new MyLisp().eval2(sexp, map);

        assertEquals(MyLispParser.parses(expect)[0], ret);
    }
}
