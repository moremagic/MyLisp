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
import mylisp.core.Sexp;

/**
 *
 * @author moremagic
 */
public class MyLispPerser {
    
    public static void main(String[] argv) {
        try {
            Sexp[] ss = parses("(null? (quote ()))");
            //Sexp[] ss = parses("(lambda (n) (+ n 1))");
            for (Sexp s : ss) {
                System.out.println(s);
            }
        } catch (Exception ex) {
            Logger.getLogger(MyLispPerser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Sexp[] parses(String sExps) throws ParseException {
        List<Sexp> ret = new ArrayList<Sexp>();
        
        int kakkoCnt = 0;
        int startIdx = 0;
        for (int i = 0; i < sExps.length(); i++) {
            String s = sExps.substring(i, i + 1);
            if (s.equals("(")) {
                kakkoCnt++;
            }
            if (s.equals(")")) {
                kakkoCnt--;
            }
            
            if (i != 0 && kakkoCnt == 0 && !s.equals(" ")) {
                ret.add(parse(sExps.substring(startIdx, i + 1)));
                startIdx = i + 1;
            }
        }
        
        return ret.toArray(new Sexp[0]);
    }
    
    public static Sexp parse(String sExps) throws ParseException {
        sExps = sExps.trim();
        return parseCell(sExps);
    }
    
    private static Cell parseCell(String sCell) throws ParseException {
        // ( .... ) の中をパースします
        List<Sexp> sexpList = new ArrayList<Sexp>();
        StringBuilder atom = new StringBuilder();
        for (int i = 1; i < sCell.length() - 1; i++) {
            String s = sCell.substring(i, i + 1);
            if (s.equals(" ")) {
                if (atom.length() != 0) {
                    sexpList.add(Atom.newAtom(atom.toString()));
                }
                atom.setLength(0);
            } else if (!s.equals("(") && !s.equals(")") && !s.isEmpty()) {
                atom.append(s);
            } else if (s.equals("(")) {
                String cellStr = getCellStr(sCell.substring(i));
                sexpList.add(parseCell(cellStr));
                
                i += cellStr.length() - 1;
            }
            
            if (s.equals(")") || i == sCell.length() - 2) {
                if (atom.length() != 0) {
                    sexpList.add(Atom.newAtom(atom.toString()));
                }
                return new Cell(sexpList.toArray(new Sexp[0]));
            }
        }
        //一つも要素がない場合、() として返却します
        if (sCell.equals("()")) {
            return new Cell();
        } else {
            throw new ParseException("Parse Error!");
        }
    }
    
    private static String getCellStr(String sexpStr) {
        int kakkoCnt = 0;
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sexpStr.length(); i++) {
            String s = sexpStr.substring(i, i + 1);
            sb.append(s);
            
            if (s.equals("(")) {
                kakkoCnt++;
            } else if (s.equals(")")) {
                kakkoCnt--;
            }
            
            if (i != 0 && kakkoCnt == 0) {
                break;
            }
        }
        return sb.toString();
    }
    
    public static class ParseException extends Exception {

        public ParseException(String message) {
            super(message);
        }
    }
}
