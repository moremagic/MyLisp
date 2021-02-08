package mylisp.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AtomNilTest {

    @Test
    void singltonTest() {
        assertTrue(AtomNil.INSTANCE == AtomNil.getInstance());
        assertTrue(AtomNil.getInstance() == AtomNil.getInstance());
    }

    @Test
    void getValue() {
        assertEquals("", AtomNil.getInstance().getValue());
    }

    @Test
    void testEquals() {
        assertEquals(AtomNil.getInstance(), AtomNil.getInstance());
    }
}