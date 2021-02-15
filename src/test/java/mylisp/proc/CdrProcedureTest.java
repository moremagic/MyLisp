package mylisp.proc;

import mylisp.MyLispParser;
import mylisp.core.Atom;
import mylisp.core.Procedure;
import mylisp.core.Sexp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
            "(cdr '(1 2 3)),((quote (1 2 3)))",
            "(hoge (fuga 1 2 3)),((fuga 1 2 3)))",
    })
    void apply(String actual, String expected) throws Atom.AtomException, Procedure.ProcedureException {
        Procedure proc = new CdrProcedure();

        Sexp actualSexp = MyLispParser.parses(actual)[0];
        Sexp expectSexp = MyLispParser.parses(expected)[0];

        assertEquals(expectSexp, proc.apply(MyLispParser.parses(actual)[0]));
    }

    void errorApply(){
        Procedure proc = new CdrProcedure();
        assertThrows(Procedure.ProcedureException.class, () -> proc.apply(MyLispParser.parses("()")[0]));
    }
}