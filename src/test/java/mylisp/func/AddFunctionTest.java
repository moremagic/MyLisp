package mylisp.func;

import mylisp.core.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddFunctionTest {

    private AddFunction addFunction = new AddFunction();

    @Test
    public void basicFunctionTest() throws FunctionException {
        IPair cons = new ConsCell(Atom.newAtom(addFunction.operatorSymbol()), new ConsCell(Atom.newAtom(5), new ConsCell(Atom.newAtom(23), Atom.NIL)));
        Map<AtomSymbol, Sexp > env = new HashMap<AtomSymbol, Sexp>();

        Sexp ret = addFunction.eval(cons, env);

        assertEquals(Atom.newAtom(28), ret);
    }

    @Test
    public void dotPairFunctionTest() throws FunctionException {
        IPair cons = new ConsCell(Atom.newAtom(addFunction.operatorSymbol()), new ConsCell(Atom.newAtom(2), Atom.newAtom(1)));
        Map<AtomSymbol, Sexp > env = new HashMap<AtomSymbol, Sexp>();

        Sexp ret = addFunction.eval(cons, env);

        assertEquals(Atom.newAtom(3), ret);
    }

    @Test
    public void operatorSymbolTest() throws FunctionException {
        assertEquals("+", addFunction.operatorSymbol());
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, 3",
            "13, 8, 21",
            "34, 2, 36",
            "23, 456, 479",
            "311, 34, 345",
            "23, -5, 18",
            "-23, 32, 9",
            "2314123, 12344123, 14658246",
            "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890, 1234567890, 123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678902469135780",
    })
    public void addTest(String first, String second, String expect) throws FunctionException {;
        Atom expectAtom = Atom.newAtom(expect);

        IPair plainPairConsCell = createPlainConsCell(first, second);
        IPair dotPairConsCell = createDotPairConsCell(first, second);
        Map<AtomSymbol, Sexp > env = new HashMap<AtomSymbol, Sexp>();

        Sexp plainPairActual = addFunction.eval(plainPairConsCell, env);
        assertEquals(expectAtom, plainPairActual);

        Sexp dotPairActual = addFunction.eval(dotPairConsCell, env);
        assertEquals(expectAtom, dotPairActual);
    }

    private ConsCell createPlainConsCell(String first, String second){
        // (+ first second)
        return new ConsCell(Atom.newAtom(addFunction.operatorSymbol()), new ConsCell(Atom.newAtom(first), new ConsCell(Atom.newAtom(second), Atom.NIL)));
    }

    private ConsCell createDotPairConsCell(String first, String second){
        // (+ first . second)
        return new ConsCell(Atom.newAtom(addFunction.operatorSymbol()), new ConsCell(Atom.newAtom(first), Atom.newAtom(second)));
    }

}
