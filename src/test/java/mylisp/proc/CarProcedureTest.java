package mylisp.proc;

import mylisp.MyLispParser;
import mylisp.core.Atom;
import mylisp.core.Procedure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarProcedureTest {

    @Test
    void procedureSymbol() {
        assertEquals("car", new CarProcedure().procedureSymbol());
    }

    @ParameterizedTest
    @CsvSource({
            "'(1)', '1'",
            "'(1 2)', '1'",
            "'(1 2 3)', '1'",
            "'((1 2) 3 4 5)', '(1 2)'",
            "'(())', '()'",
            "'((() ()) ())', '(() ())'",
            "'((()) (() ()) ())', '(())'",
    })
    void apply(String actual, String expected) throws Atom.AtomException, Procedure.ProcedureException {
        Procedure proc = new CarProcedure();
        assertEquals(MyLispParser.parses(expected)[0], proc.apply(MyLispParser.parses(actual)[0]));
    }
}