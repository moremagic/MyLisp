package mylisp.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AtomNumberTest {

    @ParameterizedTest
    @ValueSource(
            ints = {1, 23, 456, 894, -12, -456, 1234567, 0, -0})
    void getValue(int value) {
        AtomNumber atomNumber = new AtomNumber(value);
        assertEquals(Integer.class, atomNumber.getValue().getClass());
        assertEquals(new Integer(value), atomNumber.getValue());
    }

    @ParameterizedTest
    @ValueSource(
            longs = {123L, 65539L, 99999999L, -999999999L, 0L, -0L})
    void getValue(long value) {
        AtomNumber atomNumber = new AtomNumber(value);
        assertEquals(Long.class, atomNumber.getValue().getClass());
        assertEquals(new Long(value), atomNumber.getValue());
    }

    @ParameterizedTest
    @ValueSource(
            floats = {0.123F, 34.987F, -123.897F, -0.23F, 0F, -0F})
    void getValue(float value) {
        AtomNumber atomNumber = new AtomNumber(value);
        assertEquals(Float.class, atomNumber.getValue().getClass());
        assertEquals(new Float(value), atomNumber.getValue());
    }

    @ParameterizedTest
    @ValueSource(
            doubles = {0.234, 0.345, -0.234, -7664.0, -0, 0})
    void getValue(double value) {
        AtomNumber atomNumber = new AtomNumber(value);
        assertEquals(Double.class, atomNumber.getValue().getClass());
        assertEquals(new Double(value), atomNumber.getValue());
    }

    @ParameterizedTest
    @ValueSource(
            strings = {"10000000000000000000.0000", "-999999999999999999999.99999", "0", "-0"})
    void getValue(String value) {
        AtomNumber atomNumber = new AtomNumber(new BigDecimal(value));
        assertEquals(BigDecimal.class, atomNumber.getValue().getClass());
        assertEquals(new BigDecimal(value), atomNumber.getValue());
    }

    @ParameterizedTest
    @ValueSource(
            ints = {1, 23, 456, 894, -12, -456, 1234567, 0, -0})
    void testToString(int value) {
        AtomNumber atomNumber = new AtomNumber(value);
        assertEquals(new Integer(value).toString(), atomNumber.toString());
    }

    @ParameterizedTest
    @ValueSource(
            longs = {123L, 65539L, 99999999L, -999999999L, 0L, -0L})
    void testToString(long value) {
        AtomNumber atomNumber = new AtomNumber(value);
        assertEquals(new Long(value).toString(), atomNumber.toString());
    }

    @ParameterizedTest
    @ValueSource(
            floats = {0.123F, 34.987F, -123.897F, -0.23F, 0F, -0F})
    void testToString(float value) {
        AtomNumber atomNumber = new AtomNumber(value);
        assertEquals(Float.class, atomNumber.getValue().getClass());
        assertEquals(new Float(value).toString(), atomNumber.toString());
    }

    @ParameterizedTest
    @ValueSource(
            doubles = {0.234, 0.345, -0.234, -7664.0, -0, 0})
    void testToString(double value) {
        AtomNumber atomNumber = new AtomNumber(value);
        assertEquals(Double.class, atomNumber.getValue().getClass());
        assertEquals(new Double(value).toString(), atomNumber.toString());
    }

    @ParameterizedTest
    @ValueSource(
            strings = {"10000000000000000000.0000", "-999999999999999999999.99999", "0", "-0"})
    void testToString(String value) {
        AtomNumber atomNumber = new AtomNumber(new BigDecimal(value));
        assertEquals(BigDecimal.class, atomNumber.getValue().getClass());
        assertEquals(new BigDecimal(value).toString(), atomNumber.toString());
    }

    @ParameterizedTest
    @ValueSource(
            doubles = {0.234, 0.345, -0.234, -7664.0, -0, 0})
    void testEquals(Number value) {
        AtomNumber a = new AtomNumber(value);
        AtomNumber b = new AtomNumber(value);
        assertFalse(a == b);
        assertEquals(a, b);
    }

    @ParameterizedTest
    @ValueSource(
            strings = {"10000000000000000000.0000", "-999999999999999999999.99999", "0", "-0"})
    void testEquals(String value) {
        AtomNumber a = new AtomNumber(new BigDecimal(value));
        AtomNumber b = new AtomNumber(new BigDecimal(value));
        assertFalse(a == b);
        assertEquals(a, b);
    }

    @Test
    void testHashCode() {
        AtomNumber a = new AtomNumber(1);
        AtomNumber b = new AtomNumber(1);
        AtomNumber c = new AtomNumber(3);
        assertFalse(a == b);
        assertFalse(a == c);
        assertFalse(b == c);
        assertFalse(a.hashCode() == c.hashCode());
        assertFalse(b.hashCode() == c.hashCode());
        assertEquals(a.hashCode(), b.hashCode());
    }
}