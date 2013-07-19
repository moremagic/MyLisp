/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mylisp;

import java.util.ArrayList;
import java.util.List;
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
        try {
            Sexp[] ss = parses("(\"aa aa\" aaa)");
            //[] ss = parses("('a 'b 'c ('d 'e))");
            //Sexp[] ss = parses("(('()) 123456789012345)");
            //Sexp[] ss = parses("(lat? '(1 (3 4)))");
            //Sexp[] ss = parses("((x) (1 2))");
            //Sexp[] ss = parses("(lambda (x) (and (not (pair? x))))");
            //Sexp[] ss = parses("(define atom? (lambda (x) (and (not (pair? x)) (not (null? x)))))");
            for (Sexp s : ss) {
                System.out.println(s);
            }
        } catch (Exception ex) {
            Logger.getLogger(MyLispPerser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 複数行のパースを行い、S式の配列を返却する。
     * 改行が含まれるテキストのパースと
     * コメント文を無視したパースを行う
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
                return parse(sAtom.substring(i));
            } else {
                atom.append(s);
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
                Sexp atom = parseAtom(sCell.substring(i + 1));
                i += getAtomLength(sCell.substring(i + 1)) + 1;
                
                sexpList.add(new Cell(Atom.newAtom("quote"), atom));
            } else {
                Sexp atom = parseAtom(sCell.substring(i));
                i += getAtomLength(sCell.substring(i));

                sexpList.add(atom);
            }
        }

        if(!sexpList.isEmpty() && sexpList.get(0).toString().equals(Lambda.LAMBDA_SYMBOL)){
            return new Lambda(sexpList.toArray(new Sexp[0]));
        }else{
            return new Cell(sexpList.toArray(new Sexp[0]));
        }
    }

    public static int getAtomLength(String sexpStr) {
        int kakkoCnt = 0;
        int i;
        for (i = 0; i < sexpStr.length(); i++) {
            String s = sexpStr.substring(i, i + 1);
            if (s.equals("(")) {
                kakkoCnt++;
            } else if (s.equals(")")) {
                kakkoCnt--;
            }

            if (kakkoCnt == 0 && (s.equals(")") || s.equals(" "))) {
                break;
            } else if (kakkoCnt < 0) {
                i-=1;
                break;
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
