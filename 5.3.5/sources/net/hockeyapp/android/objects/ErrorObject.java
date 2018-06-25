package net.hockeyapp.android.objects;

import java.io.Serializable;

public class ErrorObject implements Serializable {
    private static final long serialVersionUID = 1508110658372169868L;
    private int mCode;
    private String mMessage;

    public int getCode() {
        return this.mCode;
    }

    public void setCode(int code) {
        this.mCode = code;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }
}
