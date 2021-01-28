package mylisp.core;

import mylisp.func.FunctionException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AbstractOperatorTest {
    AbstractOperator testOperator = new AbstractOperator() {
        @Override
        public String operatorSymbol() {
            return null;
        }

        @Override
        public Sexp eval(IPair cons, Map<AtomSymbol, Sexp> env) throws FunctionException {
            return null;
        }
    };

    @Test
    void checkArgument() throws Atom.AtomException {
        IPair cons = new ConsCell(Atom.newAtom("hoge"), new ConsCell(Atom.newAtom(1), AtomNil.INSTANCE));
        assertThrows(FunctionException.class, () -> testOperator.checkArgument(cons, 2));
        assertDoesNotThrow(() -> testOperator.checkArgument(cons, 1));
    }
}