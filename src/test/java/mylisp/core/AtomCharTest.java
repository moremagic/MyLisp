package mylisp.core;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class AtomCharTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "あああ",
            "sdf",
            "123545",
            "!!!@#",
    })
    void failNewTest(String value) {
        assertThrows(Atom.AtomException.class, () ->new AtomChar(value));
    }

    @ParameterizedTest
    @CsvSource({
            "'#\\a', 'a'",
            "'#\\b', 'b'",
            "'#\\x', 'x'",
            "'#\\dfaksldjkf', 'd'",
            "'#\\####$', '#'",
            "'#\\あ', 'あ'",
            "'#\\漢字', '漢'",
            "'#\\space', ' '",
            "'#\\tab', '\t'",
            "'#\\newline', '\n'",
    })
    void getValue(String value, String expect) throws Atom.AtomException {
        AtomChar atomChar = new AtomChar(value);
        assertEquals(expect, atomChar.getValue());
    }

    @ParameterizedTest
    @CsvSource({
            "'#\\b', 'b'",
            "'#\\x', 'x'",
            "'#\\dfaksldjkf', 'd'",
            "'#\\####$', '#'",
            "'#\\あ', 'あ'",
            "'#\\漢字', '漢'",
            "'#\\space', ' '",
            "'#\\tab', '\t'",
            "'#\\newline', '\n'",
    })
    void testToString(String value, String expect) throws Atom.AtomException {
        AtomChar atomChar = new AtomChar(value);
        assertEquals(expect, atomChar.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "#\\a",
            "#\\b",
            "#\\x",
            "#\\dfaksldjkf",
            "#\\####$",
            "#\\あ",
            "#\\漢字",
            "#\\space",
            "#\\tab",
            "#\\newline",
    })
    void testEquals(String value) throws Atom.AtomException {
        AtomChar a = new AtomChar(value);
        AtomChar b = new AtomChar(value);

        //インスタンスオブジェクトは異なる
        assertFalse(a == b);

        //別々のインスタンスでもequalsは同じになる
        assertEquals(a, b);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "#\\a",
            "#\\b",
            "#\\x",
            "#\\dfaksldjkf",
            "#\\####$",
            "#\\あ",
            "#\\漢字",
            "#\\space",
            "#\\tab",
            "#\\newline",
    })
    void testHashCode(String value) throws Atom.AtomException {
        AtomChar a = new AtomChar(value);
        AtomChar b = new AtomChar(value);

        //インスタンスオブジェクトは異なる
        assertFalse(a == b);

        //別々のインスタンスでもequalsは同じになる
        assertEquals(a.hashCode(), b.hashCode());
    }
}