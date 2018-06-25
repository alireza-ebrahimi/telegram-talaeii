package org.telegram.SQLite;

public class SQLiteException extends Exception {
    private static final long serialVersionUID = -2398298479089615621L;
    public final int errorCode;

    public SQLiteException(int errcode, String msg) {
        super(msg);
        this.errorCode = errcode;
    }

    public SQLiteException(String msg) {
        this(0, msg);
    }

    public SQLiteException() {
        this.errorCode = 0;
    }
}
