package mylisp.core;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class AtomStringTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "aa\"b\"bb",
            "\"hoge",
            "hoge\"",
            "hoge",
            "あああ",
            "sdf",
            "123545",
            "!!!@#",
            "'', ''",
            "'aaaaa', ''",
            "'123', ''",
            "'-893', ''",
            "'3.14159', ''",
            "'-1.732', ''",
            "'あいうえお', ''",
            "'漢字', ''",
            "'🛹🛹', ''",
            "'!@#$', ''",
            "'\"aaaaa', ''",
            "'あいうえお\"', ''",
    })
    void failNewTest(String value) {
        assertThrows(Atom.AtomException.class, () ->new AtomString(value));
    }

    @ParameterizedTest
    @CsvSource({
            "'\"\"', '\"\"'",
            "'\"abc\"', '\"abc\"'",
            "'\"123\"', '\"123\"'",
            "'\"あいうえお\"', '\"あいうえお\"'",
            "'\"漢字\"', '\"漢字\"'",
            "'\"!#$!@#$\"', '\"!#$!@#$\"'",
            "'\"🛹🛹\"', '\"🛹🛹\"'",
    })
    void getValue(String value, String expect) throws Atom.AtomException {
        AtomString atomString = new AtomString(value);
        assertEquals(expect, atomString.getValue());
    }

    @ParameterizedTest
    @CsvSource({
            "'\"\"', '\"\"'",
            "'\"abc\"', '\"abc\"'",
            "'\"123\"', '\"123\"'",
            "'\"あいうえお\"', '\"あいうえお\"'",
            "'\"漢字\"', '\"漢字\"'",
            "'\"!#$!@#$\"', '\"!#$!@#$\"'",
            "'\"🛹🛹\"', '\"🛹🛹\"'",
    })
    void testToString(String value, String expect) throws Atom.AtomException {
        AtomString atomString = new AtomString(value);
        assertEquals(expect, atomString.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "\"\"",
            "\"abc\"",
            "\"123\"",
            "\"あいうえお\"",
            "\"漢字\"",
            "\"'!#$!@#$'\"",
            "\"🛹🛹\"",
    })
    void testEquals(String value) throws Atom.AtomException {
        AtomString a = new AtomString(value);
        AtomString b = new AtomString(value);
        assertFalse(a == b);
        assertEquals(a, b);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "\"\"",
            "\"abc\"",
            "\"123\"",
            "\"あいうえお\"",
            "\"漢字\"",
            "\"'!#$!@#$'\"",
            "\"🛹🛹\"",
    })
    void testHashCode(String value) throws Atom.AtomException {
        AtomString a = new AtomString(value);
        AtomString b = new AtomString(value);
        assertFalse(a == b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}