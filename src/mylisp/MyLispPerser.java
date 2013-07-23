/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import mylisp.core.Atom;
import mylisp.core.Cell;
import mylisp.core.Lambda;
import mylisp.core.Sexp;

/**
 *
 * @author moremagic
 */
public class MyLispPerser {

    public static void main(String[] argv) {
        Map<String, String> testMap = new LinkedHashMap<String, String>();
        testMap.put("(\"aa bb  cc & (asdf) '(asdfasd) \' \\\" \" aaa)", "(\"aa bb  cc & (asdf) '(asdfasd) \' \\\" \" aaa)");
        testMap.put("'(1 '2 3)", "(quote (1 (quote 2) 3))");
        testMap.put("(let loop((n1 n) (p n)))", "(let loop ((n1 n) (p n)))");        
        testMap.put("(\" a b c '() '(quote) \\\" \" aaa)", "(\" a b c '() '(quote) \\\" \" aaa)");
        testMap.put("('a 'b 'c ('d 'e))", "((quote a) (quote b) (quote c) ((quote d) (quote e)))");
        testMap.put("(('()) 123456789012345)", "(((quote ())) 123456789012345)");
        testMap.put("(lat? '(1 (3 4)))", "(lat? (quote (1 (3 4))))");
        testMap.put("((x) (1 2))", "((x) (1 2))");
        testMap.put("(lambda (x) (and (not (pair? x))))", "(lambda (x) (and (not (pair? x))))");
        testMap.put("(define atom? (lambda (x) (and (not (pair? x)) (not (null? x)))))", "(define atom? (lambda (x) (and (not (pair? x)) (not (null? x)))))");

        for (Map.Entry<String, String> s : testMap.entrySet()) {
            testParse(s.getKey(), s.getValue());
        }
    }

    private static void testParse(String test_code, String expected_code) {
        try {
            Sexp[] ss = parses(test_code);
            StringBuilder sb = new StringBuilder();
            for (Sexp s : ss) {
                sb.append(s);
            }

            if (expected_code.equals(sb.toString())) {
                System.out.println("            [" + test_code + "] complete!");
            } else {
                System.out.println("original    [" + expected_code + "]\n"
                        + "ng          [" + sb.toString() + "]");
            }
        } catch (Exception ex) {
            Logger.getLogger(MyLispPerser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 複数行のパースを行い、S式の配列を返却する。 改行が含まれるテキストのパースと コメント文を無視したパースを行う
     *
     * @param sExps
     * @return
     * @throws mylisp.MyLispPerser.ParseException
     */
    public static Sexp[] parses(String sExps) throws ParseException {
        //改行とコメント行の削除
        StringBuilder sb = new StringBuilder();
        for (String line : sExps.split("\n")) {
            if (line.indexOf(";") == -1) {
                sb.append(line);
            } else {
                sb.append(line.substring(0, line.indexOf(";")));
            }
        }

        String ss = sb.toString();

        List<Sexp> ret = new ArrayList<Sexp>();
        if (ss.startsWith("(")) {
            //Cellだった場合、1つのCellを取り出してパースを行う
            int kakkoCnt = 0;
            int startIdx = 0;
            for (int i = 0; i < ss.length(); i++) {
                String s = ss.substring(i, i + 1);
                if (s.equals("(")) {
                    kakkoCnt++;
                }
                if (s.equals(")")) {
                    kakkoCnt--;
                }

                if (i != 0 && kakkoCnt == 0 && !s.equals(" ")) {
                    ret.add(parse(ss.substring(startIdx, i + 1)));
                    startIdx = i + 1;
                }
            }
        } else {
            //Atomだった場合のパース
            ret.add(parse(ss));
        }

        return ret.toArray(new Sexp[0]);
    }

    /**
     * 文字列を1つのS式に変換する
     *
     * @param sExps
     * @return
     */
    public static Sexp parse(String sExps) {
        sExps = sExps.trim();
        if (sExps.startsWith("(") && sExps.endsWith(")")) {
            return parseCell(sExps);
        } else if (sExps.startsWith("\"")) {
            return parseAtomString(sExps);
        } else if (sExps.startsWith("'")) {
            Sexp atom = parse(sExps.substring(1));
            return new Cell(Atom.newAtom("quote"), atom);
        } else {
            return parseAtom(sExps);
        }
    }

    /**
     * @param sAtom
     * @return
     */
    private static Sexp parseAtom(String sAtom) {
        StringBuilder atom = new StringBuilder();
        for (int i = 0; i < sAtom.length(); i++) {
            String s = sAtom.substring(i, i + 1);
            if (s.equals(" ") || s.equals(")")) {
                break;
            } else if (s.equals("(")) {
                break;
            } else {
                atom.append(s);
            }
        }

        return Atom.newAtom(atom.toString());
    }

    private static Sexp parseAtomString(String sAtom) {
        boolean bQuote = true;

        StringBuilder atom = new StringBuilder();
        for (int i = 0; i < sAtom.length(); i++) {
            String s = sAtom.substring(i, i + 1);
            atom.append(s);

            if (s.equals("\\")) {
                i++;
                atom.append(sAtom.substring(i, i + 1));
            } else if (s.equals("\"")) {
                bQuote = !bQuote;
            }

            if (bQuote) {
                break;
            }
        }

        return Atom.newAtom(atom.toString());
    }

    /**
     *
     * @param sCell
     * @return
     * @throws mylisp.MyLispPerser.ParseException
     */
    private static Sexp parseCell(String sCell) {
        // ( .... ) の中をパースします    
        List<Sexp> sexpList = new ArrayList<Sexp>();
        for (int i = 1; i < sCell.length() - 1; i++) {
            String s = sCell.substring(i, i + 1);
            if (s.equals(" ")) {
                continue;
            } else if (s.equals(")")) {
                break;
            } else if (s.equals("'")) {
                Sexp atom = parse(sCell.substring(i + 1));
                i += getAtomLength(sCell.substring(i + 1)) + 1;

                sexpList.add(new Cell(Atom.newAtom("quote"), atom));
            } else if (s.equals("\"")) {
                Sexp atom = parseAtomString(sCell.substring(i));
                i += atom.toString().length();

                sexpList.add(atom);
            } else {
                Sexp atom = parse(sCell.substring(i));
                i += getAtomLength(sCell.substring(i));

                sexpList.add(atom);
            }
        }

        if (!sexpList.isEmpty() && sexpList.get(0).toString().equals(Lambda.LAMBDA_SYMBOL)) {
            return new Lambda(sexpList.toArray(new Sexp[0]));
        } else {
            return new Cell(sexpList.toArray(new Sexp[0]));
        }
    }

    /**
     * パース前文字列から 最初に出現する Atom の文字数を切り出すメソッド
     *
     * @param sexpStr パース前文字列
     * @return 最初に出現する Atom の文字数
     */
    public static int getAtomLength(String sexpStr) {
        // 「()」 の数が合致するまでの文字数を返却する
        int kakkoCnt = 0;
        int i;
        for (i = 0; i < sexpStr.length(); i++) {
            String s = sexpStr.substring(i, i + 1);
            if (s.equals("(")) {
                kakkoCnt++;
            } else if (s.equals(")")) {
                kakkoCnt--;
            }

            if (!sexpStr.startsWith("(") && (s.equals("(") || s.equals(")") || s.equals(" "))) {
                //最初が 「 ( 」 で始まっていないときは組み合わせ数を無視して「(」「)」「 」 で break; する。
                i -= 1;
                break;
            } else {
                if (kakkoCnt == 0 && (s.equals("(") || s.equals(")") || s.equals(" "))) {
                    break;
                }
            }
        }
        return i;
    }

    public static class ParseException extends Exception {

        public ParseException(String message) {
            super(message);
        }
    }
}
