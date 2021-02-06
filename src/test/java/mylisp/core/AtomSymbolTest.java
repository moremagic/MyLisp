package mylisp.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class AtomSymbolTest {

    @ParameterizedTest
    @CsvSource({
            "'123', 'null'",
            "'\"abc\"', 'null'",
            "'#t', 'null'",
            "'#f', 'null'",
            "'!@#$', ''!@#$''",
            "'\"aaaaa', '\"aaaaa''",
            "'あいうえお\"', 'あいうえお\"'",
            "'\"あいうえお\"', '\"あいうえお\"'",
            "'\"漢字\"', '\"漢字\"'",
            "'\"!#$!@#$\"', '\"!#$!@#$\"'",
            "'\"🛹🛹\"', '\"🛹🛹\"'",
    })
    void getValue() {
        //TODO 未実装
    }

    @Test
    void testToString() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }
}