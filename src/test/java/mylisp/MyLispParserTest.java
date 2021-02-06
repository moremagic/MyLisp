package mylisp;

import mylisp.core.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyLispParserTest {

    @Test
    public void consCellParseTest() throws Atom.AtomException {
        Sexp[] ret = MyLispParser.parses("(+ 3 4)");

        Sexp actual = ret[0];
        Sexp expect = new ConsCell(Atom.newAtom("+"), new ConsCell(Atom.newAtom(3), new ConsCell(Atom.newAtom(4), AtomNil.INSTANCE)));

        assertEquals(actual, expect);
    }

    @Test
    public void nestConsCellParseTest() throws Atom.AtomException {
        Sexp[] ret = MyLispParser.parses("(+ 2 (+ 3 4 (+ 5 6)))");
        Sexp actual = ret[0];
        Sexp expect = new ConsCell(Atom.newAtom("+"), new ConsCell(Atom.newAtom(2),
                new ConsCell(new ConsCell(Atom.newAtom("+"), new ConsCell(Atom.newAtom(3), new ConsCell(Atom.newAtom(4),
                        new ConsCell(new ConsCell(Atom.newAtom("+"), new ConsCell(Atom.newAtom(5), new ConsCell(Atom.newAtom(6)
                                , AtomNil.INSTANCE))), AtomNil.INSTANCE)))), AtomNil.INSTANCE)));
        assertEquals(actual, expect);
    }


    //@Test
    //TODO: dotted pair に対応する
    public void dotPairParseTest() throws Atom.AtomException {
        Sexp[] ret = MyLispParser.parses("(+ 2 . 1)");

        Sexp actual = ret[0];
        Sexp expect = new ConsCell(Atom.newAtom("+"), new ConsCell(Atom.newAtom(2), Atom.newAtom(1)));

        assertEquals(actual, expect);
    }


    @ParameterizedTest
    @CsvSource({
            "(), ()",
            "(#t), (#t)",
            "(#f), (#f)",
            "(() ()), (() ())",
            "(() (() () ())), (() (() () ()))",
            "(() (() () (() ()))), (() (() () (() ())))",
            "(1 2 3), (1 2 3)",
            "(+ 2 3), (+ 2 3)",
            "(quote (eq? a #f)), (quote (eq? a #f))", //quote省略記法（'） の指定方法が不明のためとりあえずそのまま
            "'#\\(', '('",
            "'#\\)', ')'",
            "'#\\a', 'a'",
            "((eq?-c 'salada) 'salada), ((eq?-c (quote salada)) (quote salada))",
            "(define (type1 filename) (let ((iport (open-input-file filename))) \t(let loop ((c (read-char iport))) (cond ((not (eof-object? c)) (display c) (loop (read-char iport))))) (close-input-port iport))), (define (type1 filename) (let ((iport (open-input-file filename))) (let loop ((c (read-char iport))) (cond ((not (eof-object? c)) (display c) (loop (read-char iport))))) (close-input-port iport)))",
            "(test '(eq? (atom? '(atom turkey or) or) #f)), (test (quote (eq? (atom? (quote (atom turkey or)) or) #f)))",
            "(if (eq? (< 1 2) #t) (display \"OK\") (display \"NG\")), (if (eq? (< 1 2) #t) (display \"OK\") (display \"NG\"))",
            "(define (type1 filename) (let ((iport (open-input-file filename))) (let loop ((c (read-char iport))) (cond ((not (eof-object? c)) (display c) (loop (read-char iport))))) (close-input-port iport))), (define (type1 filename) (let ((iport (open-input-file filename))) (let loop ((c (read-char iport))) (cond ((not (eof-object? c)) (display c) (loop (read-char iport))))) (close-input-port iport)))",
            "(\"aa bb  cc & (asdf) '(asdfasd) \' \\\" \" aaa), (\"aa bb  cc & (asdf) '(asdfasd) \' \\\" \" aaa)",
            "'(1 '2 3), (quote (1 (quote 2) 3))",
            "(let loop((n1 n) (p n))), (let loop ((n1 n) (p n)))",
            "(\" a b c '() '(quote) \\\" \" aaa), (\" a b c '() '(quote) \\\" \" aaa)",
            "('a 'b 'c ('d 'e)), ((quote a) (quote b) (quote c) ((quote d) (quote e)))",
            "(('()) 123456789012345), (((quote ())) 123456789012345)",
            "(lat? '(1 (3 4))), (lat? (quote (1 (3 4))))",
            "((x) (1 2)), ((x) (1 2))",
            "(lambda (x) (and (not (pair? x)))), (lambda (x) (and (not (pair? x))))",
            "(define atom? (lambda (x) (and (not (pair? x)) (not (null? x))))), (define atom? (lambda (x) (and (not (pair? x)) (not (null? x)))))",
            "(define (make-bank-account amount) (lambda (n) (set! amount (+ amount n)) amount)), (define (make-bank-account amount) (lambda (n) (set! amount (+ amount n)) amount))"
    })
    void testParse(String test_code, String expected_code) throws Atom.AtomException {
        Sexp[] ss = MyLispParser.parses(test_code);
        assertEquals(expected_code, ss[0].toString());
    }

}
