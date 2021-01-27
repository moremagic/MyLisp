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
import mylisp.core.ConsCell;
import mylisp.core.IPair;
import mylisp.core.Lambda;
import mylisp.core.Sexp;

/**
 *
 * @author moremagic
 */
public class MyLispPerser {
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
        ss = ss.replaceAll("\t", "");

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
    private static Sexp parse(String sExps) {
        sExps = sExps.trim();
        if (sExps.startsWith("(") && sExps.endsWith(")")) {
            return parseCell(sExps);
        } else if (sExps.startsWith("\"")) {
            return parseAtomString(sExps);
        } else if (sExps.startsWith("'")) {
            Sexp atom = parse(sExps.substring(1));
            return new ConsCell(Atom.newAtom("quote"), new ConsCell(atom, Atom.NIL));
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
            if (s.equals(" ")) {
                break;
            } else if (!atom.toString().equals("#\\") && (s.equals("(") || s.equals(")"))) {
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

                sexpList.add(new ConsCell(Atom.newAtom("quote"), new ConsCell(atom, Atom.NIL)));
            } else if (s.equals("\"")) {
                Sexp atom = parseAtomString(sCell.substring(i));
                i += atom.toString().length() - 1;

                sexpList.add(atom);
            } else {
                Sexp atom = parse(sCell.substring(i));
                i += getAtomLength(sCell.substring(i));

                sexpList.add(atom);
            }
        }

        if(sexpList.isEmpty()){
            // () の場合
            return new ConsCell(Atom.NIL, Atom.NIL);
        }else if (sexpList.get(0).toString().equals(Lambda.LAMBDA_SYMBOL)) {
            IPair cons = (IPair) ConsCell.list2Cons(sexpList.toArray(new Sexp[0]));
            return new Lambda(cons.getCar(), cons.getCdr());
        } else {
            IPair cons = (IPair) ConsCell.list2Cons(sexpList.toArray(new Sexp[0]));
            return cons;            
        }
    }

    /**
     * パース前文字列から 最初に出現する Atom の文字数を切り出すメソッド
     *
     * @param sexpStr パース前文字列
     * @return 最初に出現する Atom の文字数
     */
    private static int getAtomLength(String sexpStr) {
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

    /**
     * 内部オブジェクト表現をコードの形に直す
     *
     * @param sexsps 内部オブジェクト配列
     * @return
     */
    public static String sexpToString(Sexp[] sexsps){
        StringBuilder sb = new StringBuilder();
        for (Sexp s : sexsps) {
            sb.append(s.toString());
        }
        return sb.toString();
    }

    public static class ParseException extends Exception {
        public ParseException(String message) {
            super(message);
        }
    }
}
