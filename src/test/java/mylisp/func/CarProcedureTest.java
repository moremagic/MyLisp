package mylisp.func;

import mylisp.core.Atom;
import mylisp.core.ConsCell;
import mylisp.core.Procedure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CarProcedureTest {

    @Test
    void procedureSymbol() {
        assertEquals("car", new CarProcedure().procedureSymbol());
    }

    @ParameterizedTest
    @CsvSource({
            "'1', '2'",
    })
    void apply(String first, String second) throws Atom.AtomException {
        Procedure proc = new CarProcedure();
        assertEquals(Atom.newAtom(first), proc.apply(new ConsCell(Atom.newAtom(first), Atom.newAtom(second))));
    }
}