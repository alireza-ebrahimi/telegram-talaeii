package org.telegram.customization.fetch.exception;

public final class EnqueueException extends RuntimeException {
    private int errorCode;

    public EnqueueException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
