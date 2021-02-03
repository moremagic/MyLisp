package mylisp.core;

/**
 * アプリケーション全体の例外クラス
 * 実際には具象化した例外クラスとして利用する
 */
public abstract class MyLispException extends Exception{
    public MyLispException(String message){
        super(message);
    }
    public MyLispException(Exception e){
        super(e);
    }
}
