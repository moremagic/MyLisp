package mylisp.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class AtomSymbolTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "\"string\"",
            "#\\a",
    })
    void failNewTest(String value) {
        assertFalse(AtomSymbol.isAtomSymbol(value));
        assertThrows(Atom.AtomException.class, () -> new AtomSymbol(value));
    }

    @ParameterizedTest
    @CsvSource({
            "'', ''",
            "',', ','",
            "'|', '|'",
            "'''', ''''",
            "'a''b', 'a''b'",
            "'aa\"b\"bb', 'aa\"b\"bb'",
            "'\"hoge', '\"hoge'",
            "'hoge\"', 'hoge\"'",
            "'hoge', 'hoge'",
            "'あああ', 'あああ'",
            "'sdf', 'sdf'",
            "'123545', '123545'",
            "'!!!@#', '!!!@#'",
            "'aaaaa', 'aaaaa'",
            "'123', '123'",
            "'-893', '-893'",
            "'3.14159', '3.14159'",
            "'-1.732', '-1.732'",
            "'あいうえお', 'あいうえお'",
            "'漢字', '漢字'",
            "'🛹🛹', '🛹🛹'",
            "'!@#`\\$', '!@#`\\$'",
            "'\"aaaaa', '\"aaaaa'",
            "'あいうえお\"', 'あいうえお\"'",
    })
    void getValue(String value, String expect) throws Atom.AtomException {
        AtomSymbol atomSymbol = new AtomSymbol(value);
        assertEquals(expect, atomSymbol.getValue());
    }

    @ParameterizedTest
    @CsvSource({
            "'', ''",
            "',', ','",
            "'|', '|'",
            "'''', ''''",
            "'a''b', 'a''b'",
            "'aa\"b\"bb', 'aa\"b\"bb'",
            "'\"hoge', '\"hoge'",
            "'hoge\"', 'hoge\"'",
            "'hoge', 'hoge'",
            "'あああ', 'あああ'",
            "'sdf', 'sdf'",
            "'123545', '123545'",
            "'!!!@#', '!!!@#'",
            "'aaaaa', 'aaaaa'",
            "'123', '123'",
            "'-893', '-893'",
            "'3.14159', '3.14159'",
            "'-1.732', '-1.732'",
            "'あいうえお', 'あいうえお'",
            "'漢字', '漢字'",
            "'🛹🛹', '🛹🛹'",
            "'!@#`\\$', '!@#`\\$'",
            "'\"aaaaa', '\"aaaaa'",
            "'あいうえお\"', 'あいうえお\"'",
    })
    void testToString(String value, String expect) throws Atom.AtomException {
        AtomSymbol atomSymbol = new AtomSymbol(value);
        assertEquals(expect, atomSymbol.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "\'",
            "aa\"b\"bb",
            "\"hoge",
            "hoge\"",
            "hoge",
            "あああ",
            "sdf",
            "123545",
            "aaaaa",
            "123",
            "-893",
            "3.14159",
            "-1.732",
            "あいうえお",
            "漢字",
            "🛹🛹",
            "!@#$",
            "\"aaaaa",
            "あいうえお\"",
    })
    void testEquals(String value) throws Atom.AtomException {
        AtomSymbol a = new AtomSymbol(value);
        AtomSymbol b = new AtomSymbol(value);
        assertFalse(a == b);
        assertEquals(a, b);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "\'",
            "aa\"b\"bb",
            "\"hoge",
            "hoge\"",
            "hoge",
            "あああ",
            "sdf",
            "123545",
            "aaaaa",
            "123",
            "-893",
            "3.14159",
            "-1.732",
            "あいうえお",
            "漢字",
            "🛹🛹",
            "!@#$",
            "\"aaaaa",
            "あいうえお\"",
    })
    void testHashCode(String value) throws Atom.AtomException {
        AtomSymbol a = new AtomSymbol(value);
        AtomSymbol b = new AtomSymbol(value);
        assertFalse(a == b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}