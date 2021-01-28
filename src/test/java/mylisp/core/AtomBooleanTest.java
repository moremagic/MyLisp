package mylisp.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AtomBooleanTest {

    @Test
    void createAtomBoolean() {
        assertEquals(AtomBoolean.class, AtomBoolean.createAtomBoolean(true).getClass());
        assertEquals(AtomBoolean.class, AtomBoolean.createAtomBoolean(false).getClass());
    }

    @Test
    void getValue() {
        assertEquals("#t", AtomBoolean.createAtomBoolean(true).getValue());
        assertEquals("#f", AtomBoolean.createAtomBoolean(false).getValue());
    }

    @Test
    void testToString() {
        assertEquals("#t", AtomBoolean.createAtomBoolean(true).toString());
        assertEquals("#f", AtomBoolean.createAtomBoolean(false).toString());
    }

    @Test
    void testEquals() {
        assertEquals(true, AtomBoolean.AtomTrue == AtomBoolean.createAtomBoolean(true));
        assertEquals(true, AtomBoolean.AtomFalse == AtomBoolean.createAtomBoolean(false));
    }

    @Test
    void testHashCode() {
        assertEquals(true, AtomBoolean.AtomTrue.hashCode() == AtomBoolean.createAtomBoolean(true).hashCode());
        assertEquals(true, AtomBoolean.AtomFalse.hashCode() == AtomBoolean.createAtomBoolean(false).hashCode());
    }
}