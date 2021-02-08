package mylisp.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AtomBooleanTest {

    @Test
    void createAtomBoolean() {
        assertEquals(AtomBoolean.class, AtomBoolean.createAtomBoolean(true).getClass());
        assertEquals(AtomBoolean.class, AtomBoolean.createAtomBoolean(false).getClass());

        assertEquals(AtomBoolean.class, AtomBoolean.createAtomBoolean("#t").getClass());
        assertEquals(AtomBoolean.class, AtomBoolean.createAtomBoolean("#f").getClass());
    }

    @Test
    void getValue() {
        assertEquals("#t", AtomBoolean.createAtomBoolean(true).getValue());
        assertEquals("#f", AtomBoolean.createAtomBoolean(false).getValue());

        assertEquals("#t", AtomBoolean.createAtomBoolean("#t").getValue());
        assertEquals("#f", AtomBoolean.createAtomBoolean("#f").getValue());
        assertEquals("#f", AtomBoolean.createAtomBoolean("hoge").getValue());

    }

    @Test
    void testToString() {
        assertEquals("#t", AtomBoolean.createAtomBoolean(true).toString());
        assertEquals("#f", AtomBoolean.createAtomBoolean(false).toString());

        assertEquals("#t", AtomBoolean.createAtomBoolean("#t").toString());
        assertEquals("#f", AtomBoolean.createAtomBoolean("#f").toString());
        assertEquals("#f", AtomBoolean.createAtomBoolean("hoge").toString());
    }

    @Test
    void testEquals() {
        assertSame(AtomBoolean.AtomTrue, AtomBoolean.createAtomBoolean(true));
        assertSame(AtomBoolean.AtomFalse, AtomBoolean.createAtomBoolean(false));

        assertSame(AtomBoolean.AtomTrue, AtomBoolean.createAtomBoolean("#t"));
        assertSame(AtomBoolean.AtomFalse, AtomBoolean.createAtomBoolean("#f"));
        assertSame(AtomBoolean.AtomFalse, AtomBoolean.createAtomBoolean("hoge"));
    }

    @Test
    void testHashCode() {
        assertEquals(AtomBoolean.createAtomBoolean(true).hashCode(), AtomBoolean.AtomTrue.hashCode());
        assertEquals(AtomBoolean.AtomFalse.hashCode(), AtomBoolean.createAtomBoolean(false).hashCode());

        assertEquals(AtomBoolean.AtomTrue.hashCode(), AtomBoolean.createAtomBoolean("#t").hashCode());
        assertEquals(AtomBoolean.AtomFalse.hashCode(), AtomBoolean.createAtomBoolean("#f").hashCode());
        assertEquals(AtomBoolean.AtomFalse.hashCode(), AtomBoolean.createAtomBoolean("hoge").hashCode());
    }
}