package mylisp.proc;

import mylisp.MyLisp;
import mylisp.MyLispParser;
import mylisp.core.Atom;
import mylisp.core.Procedure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CdrProcedureTest {

    @Test
    void procedureSymbol() {
        assertEquals("cdr", new CdrProcedure().procedureSymbol());
    }

    @ParameterizedTest
    @CsvSource({
            "'(1)', '()'",
            "'(1 2)', '(2)'",
            "'(1 2 3)', '(2 3)'",
            "'((1 2) 3 4 5)', '(3 4 5)'",
            "'(())', '()'",
            "'((() ()) ())', '(())'",
            "'((()) (() ()) ())', '((() ()) ())'",
    })
    void apply(String actual, String expected) throws Atom.AtomException, Procedure.ProcedureException {
        Procedure proc = new CdrProcedure();
        assertEquals(MyLispParser.parses(expected)[0], proc.apply(MyLispParser.parses(actual)[0]));
    }

    void errorApply(){
        Procedure proc = new CdrProcedure();
        assertThrows(Procedure.ProcedureException.class, () -> proc.apply(MyLispParser.parses("()")[0]));
    }
}