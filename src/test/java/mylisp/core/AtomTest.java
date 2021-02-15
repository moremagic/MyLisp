package mylisp.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;

class AtomTest {

    @ParameterizedTest
    @ValueSource(strings = {"#t", "#f"})
    void newAtomForBoolean(String value) throws Atom.AtomException {
        Atom expect = Atom.newAtom(value);
        assertEquals(AtomBoolean.class, expect.getClass());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void newAtomForBoolean(boolean value) throws Atom.AtomException {
        Atom expect = Atom.newAtom(value);
        assertEquals(AtomBoolean.class, expect.getClass());
        assertFalse(expect.isPair());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 34, 345, -2, -4354, -34, 3})
    void newAtomforNumber(int value) throws Atom.AtomException {
        Atom expect = Atom.newAtom(value);
        assertEquals(AtomNumber.class, expect.getClass());
        assertFalse(expect.isPair());
    }

    @ParameterizedTest
    @ValueSource(longs = {44567654, 65539, -4524322})
    void newAtomforNumber(long value) throws Atom.AtomException {
        Atom expect = Atom.newAtom(value);
        assertEquals(AtomNumber.class, expect.getClass());
        assertFalse(expect.isPair());
    }

    @ParameterizedTest
    @ValueSource(doubles = {23.45, -4534.67, 34.2345})
    void newAtomForNumber(double value) throws Atom.AtomException {
        Atom expect = Atom.newAtom(value);
        assertEquals(AtomNumber.class, expect.getClass());
        assertFalse(expect.isPair());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "92834786098709870987098708707809879087098709879278349723498239487394823423432",
            "239847298374987234.98437298734",
            "-9283748",
            "-928374972999999999999999993948",
            "-983794283.9287397293847"})
    void newAtomForNumber(String value) throws Atom.AtomException {
        Atom expect = Atom.newAtom(value);
        assertEquals(AtomNumber.class, expect.getClass());
        assertFalse(expect.isPair());
    }

    @Test
    void newAtomForStream() throws Atom.AtomException {
        Atom expect_inputstream = Atom.newAtom(new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        });
        assertEquals(AtomPort.class, expect_inputstream.getClass());

        Atom expect_outputstream = Atom.newAtom(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                //nop
            }
        });
        assertEquals(AtomPort.class, expect_outputstream.getClass());
        assertFalse(expect_inputstream.isPair());
    }

    @ParameterizedTest
    @CsvSource({
            "\"aaaa\"",
            "\"fsdf\"",
            "\"(aaaaa)\"",
            "\"###$#$#\"",
            "\"日本語\"",
            "\"🛹\"",
    })
    void newAtomString(String value) throws Atom.AtomException {
        Atom expect = Atom.newAtom(value);
        assertEquals(AtomString.class, expect.getClass());
        assertFalse(expect.isPair());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "aaaa",
            "fsdf",
            "(aaa)",
            "#4*3@s%$#df",
            "日本語",
            "🛹",
    })
    void newAtomSymbol(String value) throws Atom.AtomException {
        Atom expect = Atom.newAtom(value);
        assertEquals(AtomSymbol.class, expect.getClass());
        assertFalse(expect.isPair());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "#\\a",
            "#\\b",
            "#\\c",
            "#\\d",
            "#\\hoge",
            "#\\アアア",
            "#\\漢字",
            "#\\space",
            "#\\tab",
            "#\\newline",
            "#\\#\\",
            "#\\%$#",
    })
    void newAtomChar(String value) throws Atom.AtomException {
        Atom expect = Atom.newAtom(value);
        assertEquals(AtomChar.class, expect.getClass());
        assertFalse(expect.isPair());
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "abc",
            "@@@@@",
            "$$$###",
            "日本語テスト",
            "🛹",
            "23984",
            "-928",
            "-928374972999999999999999993948",
            "-983794283.9287397293847"})
    void getList(String value) throws Atom.AtomException {
        assertEquals(Sexp[].class, Atom.newAtom(value).getList().getClass());
    }

}