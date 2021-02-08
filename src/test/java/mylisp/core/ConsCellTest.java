package mylisp.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class ConsCellTest {

    @ParameterizedTest
    @CsvSource({
            "'123', '456'",
            "'-233', '99999999999999'",
            "'123', 'あいう'",
            "'#t', '#f'",
    })
    void getCar(String first, String second) throws Atom.AtomException {
        ConsCell consCell = new ConsCell(Atom.newAtom(first), Atom.newAtom(second));
        assertEquals(Atom.newAtom(first), consCell.getCar());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "hoge",
            "fuga",
            "piyo",
            "123",
    })
    void setCar(String value) throws Atom.AtomException {
        ConsCell consCell = new ConsCell();
        consCell.setCar(Atom.newAtom(value));
        assertEquals(Atom.newAtom(value), consCell.getCar());
    }

    @ParameterizedTest
    @CsvSource({
            "'123', '456'",
            "'-233', '99999999999999'",
            "'123', 'あいう'",
            "'#t', '#f'",
    })
    void getCdr(String first, String second) throws Atom.AtomException {
        ConsCell consCell = new ConsCell(Atom.newAtom(first), Atom.newAtom(second));
        assertEquals(Atom.newAtom(second), consCell.getCdr());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "hoge",
            "fuga",
            "piyo",
            "123",
    })
    void setCdr(String value) throws Atom.AtomException {
        ConsCell consCell = new ConsCell();
        consCell.setCdr(Atom.newAtom(value));
        assertEquals(Atom.newAtom(value), consCell.getCdr());
    }

    @ParameterizedTest
    @CsvSource({
            "'abc', '200'",
            "'123', '20'",
            "'-233', '32'",
            "'あいうえお', '3'",
            "'#t', '221'",
            "'#\\a', '98'",
            "'symbol', '67'",
            "'\"hoge\"', '23'",
    })
    void getList(String first, String second) throws Atom.AtomException {
        long count = Long.parseLong(second);

        ConsCell consCell = new ConsCell();
        consCell.setCar(Atom.newAtom(first));
        for (long i = 0; i < count; i++) {
            consCell.setCdr(new ConsCell(Atom.newAtom(first), consCell.getCdr()));
        }

        //Sexp の数が一致していることを確認
        assertEquals( count + 1, consCell.getList().length);

        List<Sexp> list = new ArrayList<>(Arrays.asList(consCell.getList()));
        Predicate<? super Sexp> isCar = Predicate.isEqual(consCell.getCar());
        Predicate<Sexp> predicate = sexp -> !isCar.test(sexp);
        //Carと異なるSexp が存在しないことを確認
        assertFalse(list.removeIf(predicate));

    }

    @ParameterizedTest
    @CsvSource({
            "'123', '456'",
            "'-233', '99999999999999'",
            "'123', 'あいう'",
            "'#t', '#f'",
    })
    void testEquals(String first, String second) throws Atom.AtomException {
        ConsCell a = new ConsCell(Atom.newAtom(first), Atom.newAtom(second));
        ConsCell b = new ConsCell(Atom.newAtom(first), Atom.newAtom(second));

        assertNotSame(a, b);
        assertEquals(a, b);
    }
}