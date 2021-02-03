package mylisp.core;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class AtomStringTest {

    //TODO AtomStringを作る時にErrorが出るパターンのテストを作る

    @ParameterizedTest
    @CsvSource({
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
            "'\"abc\"', '\"abc\"'",
            "'\"あいうえお\"', '\"あいうえお\"'",
            "'\"漢字\"', '\"漢字\"'",
            "'\"!#$!@#$\"', '\"!#$!@#$\"'",
            "'\"🛹🛹\"', '\"🛹🛹\"'",
    })
    void getValue(String value, String expect) {
        AtomString atomString = new AtomString(value);
        assertEquals(expect, atomString.getValue());
    }

    @ParameterizedTest
    @CsvSource({
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
            "'\"abc\"', '\"abc\"'",
            "'\"あいうえお\"', '\"あいうえお\"'",
            "'\"漢字\"', '\"漢字\"'",
            "'\"!#$!@#$\"', '\"!#$!@#$\"'",
            "'\"🛹🛹\"', '\"🛹🛹\"'",
    })
    void testToString(String value, String expect) {
        AtomString atomString = new AtomString(value);
        assertEquals(expect, atomString.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "\"hoge\"",
            "\"abc\"'",
            "\"あいうえお\"",
            "\"漢字\"",
            "\"'!#$!@#$'\"",
            "\"🛹🛹\"",
    })
    void testEquals(String value) {
        AtomString a = new AtomString(value);
        AtomString b = new AtomString(value);
        assertFalse(a == b);
        assertEquals(a, b);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "hoge",
            "\"hoge\"",
            "\"abc\"'",
            "\"あいうえお\"",
            "\"漢字\"",
            "\"'!#$!@#$'\"",
            "\"🛹🛹\"",
    })
    void testHashCode(String value) {
        AtomString a = new AtomString(value);
        AtomString b = new AtomString(value);
        assertFalse(a == b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}