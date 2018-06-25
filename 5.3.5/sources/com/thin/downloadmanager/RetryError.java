package com.thin.downloadmanager;

public class RetryError extends Exception {
    public RetryError() {
        super("Maximum retry exceeded");
    }

    public RetryError(Throwable cause) {
        super(cause);
    }
}
